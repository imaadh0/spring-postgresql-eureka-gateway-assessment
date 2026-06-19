# PostgreSQL Microservice With Eureka and Spring API Gateway

This project is a Spring Boot assessment implementation with:

- One PostgreSQL-backed microservice that saves and lists item records
- Eureka Server for service registration
- Spring Cloud API Gateway for all external API traffic
- PostgreSQL database/table setup script

Client requests should be sent through the API Gateway only:

```text
http://localhost:8080/api/items
```

## Project Structure

| Module | Port | Description |
| --- | --- | --- |
| `eureka-server` | `8761` | Eureka service registry and dashboard |
| `api-gateway` | `8080` | Public API entry point |
| `item-service` | `8081` | PostgreSQL-backed microservice registered with Eureka |

## Requirements Covered

- PostgreSQL database and `items` table
- Spring Boot microservice for insert/list operations
- `POST /items` API in the microservice
- `GET /items` API in the microservice
- Microservice registration with Eureka
- API Gateway route to the microservice through Eureka load balancing
- Postman testing through the Gateway URL
- Eureka dashboard visibility with `ITEM-SERVICE` marked `UP`

## Prerequisites

Install and configure:

- Java 17
- Apache Maven
- PostgreSQL running locally on port `5432`

Check Java and Maven:

```powershell
java -version
mvn -version
```

## Database Setup

The application is configured to use:

```text
Database: assessment_db
Username: postgres
Password: 123
Port: 5432
```

If your PostgreSQL password is different, update:

```text
item-service/src/main/resources/application.yml
```

Create the database:

```sql
CREATE DATABASE assessment_db;
```

The table can be created manually with `sql/setup.sql`, or automatically by Spring JPA when `item-service` starts.

Manual table SQL:

```sql
CREATE TABLE IF NOT EXISTS items (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    created_at TIMESTAMP NOT NULL
);
```

## Build

From the project root:

```powershell
mvn clean package -DskipTests
```

## Run The Services

Open three terminals from the project root.

Terminal 1:

```powershell
mvn -pl eureka-server spring-boot:run
```

Terminal 2:

```powershell
mvn -pl item-service spring-boot:run
```

Terminal 3:

```powershell
mvn -pl api-gateway spring-boot:run
```

Startup order should be:

1. Eureka Server
2. Item Service
3. API Gateway

## Verify Eureka

Open:

```text
http://localhost:8761
```

Expected result:

```text
ITEM-SERVICE  UP
API-GATEWAY   UP
```

## Test With Postman Through API Gateway

Do not call `http://localhost:8081` for assessment testing. Use the Gateway URL.

### Save Data

Request:

```http
POST http://localhost:8080/api/items
Content-Type: application/json
```

Body:

```json
{
  "name": "Test item",
  "description": "Created from Postman"
}
```

Expected response:

```json
{
  "id": 1,
  "name": "Test item",
  "description": "Created from Postman",
  "createdAt": "2026-06-19T22:05:11.930022"
}
```

### List Data

Request:

```http
GET http://localhost:8080/api/items
```

Expected response:

```json
[
  {
    "id": 1,
    "name": "Test item",
    "description": "Created from Postman",
    "createdAt": "2026-06-19T22:05:11.930022"
  }
]
```

## API Gateway Route

The Gateway forwards:

```text
/api/items/**
```

to:

```text
lb://ITEM-SERVICE/items/**
```

This means the Gateway resolves `ITEM-SERVICE` through Eureka instead of using a direct hardcoded microservice URL.

## Notes

- `item-service` runs on port `8081`, but assessment API calls should go through `api-gateway` on port `8080`.
- The first Gateway request after startup may take a few seconds while Eureka discovery refreshes.
- Generated files such as `target/` and `*.log` files are not required for submission.
