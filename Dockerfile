# Stage 1: build using the Maven wrapper bundled in the project 

FROM eclipse-temurin:25-jdk-alpine AS builder 

WORKDIR /workspace 

 

# Install Maven explicitly since no maven base image exists for Java 25 yet 

RUN apk add --no-cache curl && \ 

    curl -fsSL https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz \ 

    | tar -xz -C /opt && \ 

    ln -s /opt/apache-maven-3.9.6/bin/mvn /usr/local/bin/mvn 
	# Copy pom first — lets Docker cache the dependency layer 

COPY pom.xml . 

RUN mvn dependency:go-offline -q 



	# Copy source and build 

COPY src ./src 

RUN mvn package -DskipTests -q 



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