# Stage 1: build 
ARG MAVEN_TAG=3.9.6-eclipse-temurin-25-jdk-alpine
FROM maven:${MAVEN_TAG} AS builder
WORKDIR /workspace

COPY pom.xml .
COPY src ./src
RUN mvn clean install

# Download dependencies separately so Docker can cache this layer 
RUN --mount=type=cache,target=/root/.m2 \ 
    ./mvnw dependency:go-offline -q 


RUN --mount=type=cache,target=/root/.m2 \ 
    ./mvnw package -DskipTests -q 

# Stage 2: extract layered jar (Spring Boot layered-jar for smaller images) 
FROM eclipse-temurin:25-jdk-alpine AS extractor 
WORKDIR /workspace 
COPY --from=builder /workspace/target/*.jar app.jar 
RUN java -Djarmode=layertools -jar app.jar extract 

# Stage 3: final runtime image 
FROM eclipse-temurin:25-jre-alpine 
WORKDIR /app 

# Copy layers (least-changed → most-changed for better cache hits) 
COPY --from=extractor /workspace/dependencies/          ./ 
COPY --from=extractor /workspace/spring-boot-loader/    ./ 
COPY --from=extractor /workspace/snapshot-dependencies/ ./ 
COPY --from=extractor /workspace/application/           ./ 

# Non-root user 
RUN addgroup -S tasker && adduser -S tasker -G tasker 
USER tasker 


EXPOSE 8080 


ENTRYPOINT ["java", \ 
  "-XX:+UseContainerSupport", \ 
  "-XX:MaxRAMPercentage=75.0", \ 
  "org.springframework.boot.loader.launch.JarLauncher"] 
