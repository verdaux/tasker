# Tasker API – Task Management Microservice

![Java](https://img.shields.io/badge/Java-25-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4-green)
![Maven](https://img.shields.io/badge/Maven-3.8.7-red)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)

---

## Overview

**Tasker API** is a modern, production-grade **Task Management REST API** built using **Spring Boot 4** and **Java 25**. It allows teams and developers to efficiently manage tasks, track progress, and organize workflows in a structured and scalable way.  

This API demonstrates **best practices in backend development**, including clean architecture, DTOs, MapStruct mapping, global exception handling, validation, and production-ready configurations.

The project is designed as a **reusable microservice** that can easily be integrated into larger platforms such as project management tools, employee workflow systems, or SaaS dashboards.

---

## Key Features

- **Create Tasks**: Add new tasks with title, description, status, and automatic timestamping.
- **Read Tasks**: Fetch all tasks or retrieve by unique UUID.
- **Clean Architecture**: Separation of layers for **Controller → Service → Repository → Entity → DTO → Mapper → Exception → Config**.
- **DTO & Mapper**: API and database layer decoupled using **DTOs** and **MapStruct** for type-safe mapping.
- **Validation**: Input validation to prevent bad data from entering the system.
- **Global Exception Handling**: Standardized API error responses.
- **Production-ready Configuration**: CORS setup, Jackson JSON customization, and PostgreSQL integration.
- **UUID-based Task IDs**: Ensures globally unique task identifiers.

---

## Tech Stack

| Layer | Technology | Description |
|-------|-----------|-------------|
| Language | Java 25 | Modern, high-performance Java version |
| Framework | Spring Boot 4 | Rapid development and production-ready microservices |
| ORM | Spring Data JPA + Hibernate | Simplifies database operations and mapping |
| Database | PostgreSQL | Reliable relational database with UUID support |
| Mapper | MapStruct | Compile-time, type-safe mapping between DTOs and Entities |
| Code Generation | Lombok | Boilerplate reduction for getters, setters, constructors, builders |
| Validation | Jakarta Bean Validation | Enforces constraints on API input |
| API Documentation | SpringDoc OpenAPI (optional) | Auto-generated API docs for consumers |
| Build | Maven | Dependency management and build automation |

---

## Architecture Diagram

<!-- ![Tasker API Architecture](./docs/tasker-architecture.png)  -->

**High-level Architecture:**
─────────────────────────────────────────────
               Tasker API Architecture
─────────────────────────────────────────────

           ┌───────────────────────────┐
           │       Controller Layer    │
           │   TaskController (REST)   │
           │   Handles HTTP requests   │
           └─────────────┬─────────────┘
                         │
                         ▼
           ┌───────────────────────────┐
           │        Service Layer      │
           │      TaskService          │
           │ Business logic & validation│
           └─────────────┬─────────────┘
                         │
                         ▼
           ┌───────────────────────────┐
           │        Mapper Layer       │
           │   TaskMapper (MapStruct) │
           │ DTO ↔ Entity conversion  │
           └─────────────┬─────────────┘
                         │
                         ▼
           ┌───────────────────────────┐
           │      Repository Layer     │
           │  TaskRepository (JPA)    │
           │  DB interactions         │
           └─────────────┬─────────────┘
                         │
                         ▼
           ┌───────────────────────────┐
           │      PostgreSQL DB        │
           │   tasks table (UUID PK)  │
           │ id, title, desc, status, │
           │       createdAt          │
           └───────────────────────────┘

Additional Components:
 ┌─────────────────────────────────────────┐
 │ DTOs (CreateTaskRequest / TaskResponse) │
 │ Entities (Task)                         │
 │ Global Exception Handler                │
 │ Configuration (CORS, Jackson, etc.)    │
 └─────────────────────────────────────────┘



---

## Example Usage

### Create a Task (POST)

**Endpoint:**

POST /tasks

**Body:**
```json
{
  "title": "Fix login bug",
  "description": "Users cannot log in using Google OAuth",
  "status": "OPEN"
}
```
Response:
```json
{
  "id": "c5c9f9d6-b8a7-4e9e-8c9c-9d3e6f3f2b0e",
  "title": "Fix login bug",
  "description": "Users cannot log in using Google OAuth",
  "status": "OPEN",
  "createdAt": "2026-03-10T18:10:22Z"
}
```
Get All Tasks (GET)
Endpoint:

GET /tasks

Response:

```json
[
  {
    "id": "c5c9f9d6-b8a7-4e9e-8c9c-9d3e6f3f2b0e",
    "title": "Fix login bug",
    "description": "Users cannot log in using Google OAuth",
    "status": "OPEN",
    "createdAt": "2026-03-10T18:10:22Z"
  }
]
```

Getting Started
1. Clone the repository:
```
git clone <repo-url>
cd tasker-api
```
2. Set environment variables for PostgreSQL:
```
export DB_URL=jdbc:postgresql://localhost:5432/taskdb
export user=postgres
export password=postgres
```

3. Run the app with Maven:
```
mvn spring-boot:run
```

4. Access endpoints using Postman or curl:
```
POST http://localhost:8080/tasks
GET  http://localhost:8080/tasks
```
