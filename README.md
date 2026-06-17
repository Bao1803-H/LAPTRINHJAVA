# Journal Tracker

Scientific journal publication tracking system - Backend API.

## Tech Stack

- Java 21
- Spring Boot 4.0.x
- Spring Data JPA
- MySQL 8
- JWT Authentication
- Docker & Docker Compose

## Prerequisites

- JDK 21+
- Docker Desktop
- Maven 3.8+

## Quick Start

### 1. Start MySQL Database

```bash
docker compose up -d db
```

MySQL will run on port 3307 (external) / 3306 (internal).

### 2. Run the Application

```bash
mvn spring-boot:run -DskipTests
```

Or with custom DB port:

```bash
set DB_PORT=3307
mvn spring-boot:run -DskipTests
```

App will start on `http://localhost:8080`

### 3. Verify

```bash
curl http://localhost:8080/api/ping
```

## API Endpoints

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login and get JWT token |
| POST | `/api/auth/logout` | Logout |
| GET | `/api/auth/me` | Get current user info |

### Example Request

```bash
# Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"user@test.com","password":"password123","displayName":"testuser"}'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@test.com","password":"password123"}'

# Get current user (requires JWT)
curl http://localhost:8080/api/auth/me \
  -H "Authorization: Bearer <your-jwt-token>"
```

## Run with Docker (Full Stack)

```bash
docker compose up --build
```

Access the app at `http://localhost:8081`

## Configuration

Environment variables (or edit `application.properties`):

| Variable | Default | Description |
|----------|---------|-------------|
| DB_HOST | localhost | Database host |
| DB_PORT | 3306 | Database port |
| DB_NAME | su26swp06 | Database name |
| DB_USERNAME | journal_user | Database user |
| DB_PASSWORD | journal_pass | Database password |
| JWT_SECRET | (default) | JWT signing key |
| JWT_EXPIRATION_MS | 3600000 | Token expiry (1 hour) |

## Project Structure

```
src/main/java/com/su26swp06/journaltracker/
├── config/           # Spring configuration
├── domain/           # Entities and enums
├── dto/              # Data transfer objects
├── exception/        # Exception handling
├── repository/       # JPA repositories
├── security/        # JWT and security
├── service/         # Business logic
└── web/             # REST controllers
```

## Current Status

- ✅ Auth API (register, login, logout, me)
- ✅ JWT token generation and validation
- ✅ MySQL database with Docker
- 🔄 Entities and repositories
- ⏳ Additional API endpoints (in progress)
