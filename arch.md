tasker/ 

├── .github/ 

│   └── workflows/ 

│       └── ci-cd.yml                  ← GitHub Actions pipeline 

├── docker/ 

│   └── init-db/ 

│       └── init.sql                   ← Optional seed SQL 

├── src/ 

│   ├── main/ 

│   │   ├── java/com/taskapi/tasker/ 

│   │   │   ├── TaskerApplication.java 

│   │   │   │ 

│   │   │   ├── config/ 

│   │   │   │   ├── SecurityConfig.java 

│   │   │   │   ├── GraphQlConfig.java 

│   │   │   │   └── MongoConfig.java 

│   │   │   │ 

│   │   │   ├── auth/ 

│   │   │   │   ├── AuthController.java      ← REST: /api/auth/register, /login 

│   │   │   │   ├── AuthService.java 

│   │   │   │   ├── JwtService.java 

│   │   │   │   ├── JwtAuthFilter.java 

│   │   │   │   └── dto/ 

│   │   │   │       ├── LoginRequest.java 

│   │   │   │       ├── RegisterRequest.java 

│   │   │   │       └── AuthResponse.java 

│   │   │   │ 

│   │   │   ├── user/ 

│   │   │   │   ├── User.java               ← JPA entity (PostgreSQL) 

│   │   │   │   ├── UserRepository.java 

│   │   │   │   └── UserService.java 

│   │   │   │ 

│   │   │   ├── task/ 

│   │   │   │   ├── Task.java               ← JPA entity (PostgreSQL) 

│   │   │   │   ├── TaskRepository.java 

│   │   │   │   ├── TaskService.java 

│   │   │   │   ├── TaskResolver.java       ← @QueryMapping / @MutationMapping 

│   │   │   │   ├── TaskStatus.java         ← Enum: TODO, IN_PROGRESS, DONE 

│   │   │   │   └── TaskPriority.java       ← Enum: LOW, MEDIUM, HIGH 

│   │   │   │ 

│   │   │   └── audit/ 

│   │   │       ├── AuditLog.java           ← MongoDB document 

│   │   │       ├── AuditRepository.java 

│   │   │       └── AuditService.java 

│   │   │ 

│   │   └── resources/ 

│   │       ├── application.yml 

│   │       ├── application-docker.yml      ← Overrides for container env 

│   │       ├── graphql/ 

│   │       │   └── schema.graphqls         ← Schema-first GraphQL 

│   │       └── db/migration/ 

│   │           ├── V1__create_users.sql 

│   │           └── V2__create_tasks.sql 

│   │ 

│   └── test/ 

│       └── java/com/taskapi/tasker/ 

│           ├── task/ 

│           │   ├── TaskResolverTest.java 

│           │   └── TaskServiceTest.java 

│           └── auth/ 

│               └── AuthServiceTest.java 

│ 

├── Dockerfile 

├── docker-compose.yml 

├── docker-compose.override.yml         ← Local dev overrides 

├── .dockerignore 

├── .gitignore 

└── pom.xml 