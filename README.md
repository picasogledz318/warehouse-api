
# Warehouse API – Documented Version

## Architecture
This project uses a classic layered architecture:

Controller → Service → Repository → Database

- Controller: Handles HTTP requests
- Service: Business logic
- Repository: Database access via JPA
- Database: H2 in-memory

## Flow
1. Client creates item
2. Variants stored with stock
3. Before selling, stock is validated

## Libraries Used
- Spring Boot Web: REST API
- Spring Data JPA: ORM
- H2: In-memory DB
- Lombok: Boilerplate reduction

## Run Locally
```bash
mvn clean package
mvn spring-boot:run
```

## Run with Docker
```bash
mvn clean package
docker-compose up --build
```

## Endpoints
- POST /api/items
- GET /api/items
- POST /api/orders/validate

## Postman
Import postman/WarehouseAPI.postman_collection.json
