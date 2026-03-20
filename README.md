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

## Tasker — End-to-End Test Guide 

Base URL: http://localhost:8080 

 
 

### 1. Health Check 

**curl** 
```bash
curl http://localhost:8080/actuator/health 
 ```

**Postman** 

Method: GET 

URL: http://localhost:8080/actuator/health 

Expected 
```json
{ "status": "UP" } 
 ```

 
 

### 2. Register a User 

**curl** 
```bash
curl -X POST http://localhost:8080/api/auth/register \ 
  -H "Content-Type: application/json" \ 
  -d '{"username":"testuser","email":"test@example.com","password":"password123"}' 
 ```

**Postman** 

Method: POST 

URL: http://localhost:8080/api/auth/register 

Body → raw → JSON: 
```json
{ 
  "username": "testuser", 
  "email": "test@example.com", 
  "password": "password123" 
} 
```

Expected 
```json
{ 
  "token": "eyJhbGciOiJIUzI1NiJ9...", 
  "username": "testuser", 
  "email": "test@example.com" 
} 
``` 

Copy the token value — you'll need it for all subsequent requests. 

 
 

### 3. Login 

**curl**
```bash
curl -X POST http://localhost:8080/api/auth/login \ 
  -H "Content-Type: application/json" \ 
  -d '{"username":"testuser","password":"password123"}' 
``` 

**Postman** 

Method: POST 

URL: http://localhost:8080/api/auth/login 

Body → raw → JSON: 
```json
{ 
  "username": "testuser", 
  "password": "password123" 
} 
``` 

Expected: same response shape as register with a fresh token. 

 
 

### 4. Set the Auth Token 

All GraphQL requests below require the JWT in the Authorization header. 
```bash
curl — export it once as a shell variable: 

export TOKEN="eyJhbGciOiJIUzI1NiJ9..."   # paste your token here 
 ```

Then every curl command below uses -H "Authorization: Bearer $TOKEN". 

**Postman** — two options: 

Option A (per-request): In each request go to Authorization tab → Type: Bearer Token → paste the token. 

Option B (collection-level, recommended): 

Create a collection called Tasker 

Collection → Edit → Authorization tab → Type: Bearer Token → paste token 

Each request inside inherits it automatically 

 
 

### 5. GraphQL — Create a Task 

**curl** 
```bash
curl -X POST http://localhost:8080/graphql \ 
  -H "Content-Type: application/json" \ 
  -H "Authorization: Bearer $TOKEN" \ 
  -d '{ 
    "query": "mutation { createTask(input: { title: \"Buy groceries\", description: \"Milk, eggs, bread\", priority: HIGH }) { id title status priority } }" 
  }' 
 ```

**Postman** 

Method: POST 

URL: http://localhost:8080/graphql 

Body → raw → JSON: 
```json
{ 
  "query": "mutation { createTask(input: { title: \"Buy groceries\", description: \"Milk, eggs, bread\", priority: HIGH }) { id title status priority } }" 
} 
``` 

Expected 
```json
{ 
  "data": { 
    "createTask": { 
      "id": "uuid-here", 
      "title": "Buy groceries", 
      "status": "TODO", 
      "priority": "HIGH" 
    } 
  } 
} 
``` 

Copy the id — needed for update/delete below. 

 
 

### 6. GraphQL — Get My Tasks 

**curl**
```bash
curl -X POST http://localhost:8080/graphql \ 
  -H "Content-Type: application/json" \ 
  -H "Authorization: Bearer $TOKEN" \ 
  -d '{"query": "query { myTasks { id title status priority dueDate } }"}' 
 ```

**Postman** — same setup, body: 
```json
{ 
  "query": "query { myTasks { id title status priority dueDate } }" 
} 
 ```

 
 

### 7. GraphQL — Update Task Status 
```bash
curl -X POST http://localhost:8080/graphql \ 
  -H "Content-Type: application/json" \ 
  -H "Authorization: Bearer $TOKEN" \ 
  -d '{ 
    "query": "mutation { updateTaskStatus(id: \"PASTE_TASK_ID\", status: IN_PROGRESS) { id title status } }" 
  }' 
 ```

Expected 
```json
{ 
  "data": { 
    "updateTaskStatus": { 
      "id": "...", 
      "title": "Buy groceries", 
      "status": "IN_PROGRESS" 
    } 
  } 
} 
``` 

 
 

### 8. GraphQL — Update Task Details 
```bash
curl -X POST http://localhost:8080/graphql \ 
  -H "Content-Type: application/json" \ 
  -H "Authorization: Bearer $TOKEN" \ 
  -d '{ 
    "query": "mutation { updateTask(id: \"PASTE_TASK_ID\", input: { title: \"Buy groceries & snacks\", priority: MEDIUM }) { id title priority } }" 
  }' 
 ```

 
 

### 9. GraphQL — Delete a Task 

curl -X POST http://localhost:8080/graphql \ 
  -H "Content-Type: application/json" \ 
  -H "Authorization: Bearer $TOKEN" \ 
  -d '{ 
    "query": "mutation { deleteTask(id: \"PASTE_TASK_ID\") }" 
  }' 
 

Expected 
```json
{ "data": { "deleteTask": true } } 
``` 

 
 

### 10. Postman — GraphQL Mode (cleaner alternative) 

Postman has a native GraphQL mode that gives you schema introspection and autocomplete: 

New Request → change type to GraphQL 

URL: http://localhost:8080/graphql 

Authorization tab → Bearer Token → paste token 

Click Schema → Fetch from URL → it auto-loads your schema 

Write queries in the Query editor with full autocomplete 

 
 

### 11. Verify Audit Logs in MongoDB 

docker exec -it tasker-mongo mongosh tasker_audit \ 
  --eval "db.audit_logs.find().pretty()" 
 

Every create/update/delete should have written a document here. 

 
 | Step | Endpoint                         | Method        |
|------|----------------------------------|--------------|
| 1    | /actuator/health                | GET          |
| 2    | /api/auth/register              | POST         |
| 3    | /api/auth/login                 | POST         |
| 4    | /graphql — createTask           | POST         |
| 5    | /graphql — myTasks              | POST         |
| 6    | /graphql — updateTaskStatus     | POST         |
| 7    | /graphql — updateTask           | POST         |
| 8    | /graphql — deleteTask           | POST         |
| 9    | MongoDB audit check             | docker exec  |



---

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
