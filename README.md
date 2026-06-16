# Journal Tracker

Spring Boot + Spring Data JPA project for the scientific journal publication tracking system.

## Tech stack

- Java 21
- Spring Boot 4.0.x
- Spring Data JPA
- MySQL 8

## Run locally

1. Create a MySQL database named `su26swp06`
2. Set database credentials in `src/main/resources/application.properties`
3. Run:

```bash
mvn spring-boot:run
```

## Run with Docker

1. Copy `.env.example` to `.env`
2. Adjust credentials if needed
3. Start both MySQL and the app:

```bash
docker compose up --build
```

4. Open:

```text
http://localhost:8080/api/ping
```

## Current status

- Core project scaffold is in place
- JPA entities are mapped from the project schema
- A simple `/api/ping` endpoint is available for a quick smoke test
- Docker compose is ready for local dev and demo runs
