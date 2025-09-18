# Winbou Prices API

This project implements an API-first approach using OpenAPI specification.

---

## 📑 API First

The API contract is defined in [`openapi.yaml`](./src/main/resources/static/openapi.yaml).

- The API is designed first and code is generated/implemented against this contract.
- You can explore the endpoints using Swagger UI once the application is running:

## 📖 API Documentation

Swagger UI is available once the application is running:

- [Swagger UI](http://localhost:8080/swagger-ui.html)

## Domain layer

The domain layer was implemented following hexagonal architecture and DDD principles:
- `Price` aggregate with validation rules
- Domain exceptions (`InvalidPriceException`, `PriceNotFoundException`)
- `PriceRepository` port abstraction
- `PriceDomainService` encapsulating business logic (select applicable price by priority)

This layer is fully independent of infrastructure and frameworks.

## 💾 Persistence layer

The persistence layer was implemented using Spring Data JPA with an H2 in-memory database:

- `PriceEntity` and `PriceId` model the `PRICES` table structure.
- `SpringDataPriceRepository` is the JPA repository interface.
- `PriceRepositoryImpl` is the adapter that implements the domain `PriceRepository` port, delegating to JPA.
- `PriceEntityMapper` handles the conversion between JPA entities and domain models.

The database schema and initial data are loaded from:
- [`schema.sql`](./src/main/resources/schema.sql)
- [`data.sql`](./src/main/resources/data.sql)


## ⚙️ Application layer

The application layer implements the system use cases and coordinates domain + persistence:

- `GetApplicablePriceUseCase` orchestrates the retrieval of applicable prices.


## 🌐 REST layer

The REST layer exposes the API to clients:

- `PriceController` defines the `/prices` endpoint.
- `PriceRestMapper` converts domain `Price` objects into `PriceResponse` DTOs.
- `GlobalExceptionHandler` handles domain exceptions and maps them to proper HTTP status codes:
  - `PriceNotFoundException` → **404 Not Found**
  - `InvalidPriceException` → **400 Bad Request**


## 🧪 Tests
The project includes:
- ✅ **Domain unit tests** for validation and business rules.  
- ✅ **Persistence adapter tests** with mocked repositories.  
- ✅ **REST controller tests** with `MockMvc`, covering:
  - Successful price retrieval (`200 OK`)
  - Price not found (`404 Not Found`)
  - Invalid request (`400 Bad Request`)
  
Run unit tests with:
```bash
mvn test
```

## 🧪 Functional Tests (Cucumber)

The project includes **end-to-end functional tests** using [Cucumber](https://cucumber.io/) and RestAssured.  
These tests cover the 5 scenarios requested in the assignment:

1. 2020-06-14 10:00:00 → Price 35.50  
2. 2020-06-14 16:00:00 → Price 25.45  
3. 2020-06-14 21:00:00 → Price 35.50  
4. 2020-06-15 10:00:00 → Price 30.50  
5. 2020-06-16 21:00:00 → Price 38.95  

Run tests with:

```bash
mvn test
```

After execution, reports are generated at:

HTML report: target/cucumber-reports/cucumber.html

JSON report: target/cucumber-reports/cucumber.json

Open cucumber.html in a browser to explore the scenarios and results.


## 🚀 Running the app

Start the Spring Boot application with:

```bash
mvn spring-boot:run
```

By default, the service will be available at:
http://localhost:8080

Example request
GET http://localhost:8080/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1


Example successful response (200 OK)

{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}

Example error response (404 Not Found)

{
  "timestamp": "2025-09-18T20:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "No applicable price found for brand=1, product=35455",
  "path": "/prices"
}


## 📬 Postman collection

A Postman collection is included in [`postman/Inditex Prices API.postman_collection.json`](./postman/Inditex Prices API.postman_collection.json).  
It contains predefined requests covering the required test cases:

1. Request at `2020-06-14 10:00` → product 35455, brand 1  
2. Request at `2020-06-14 16:00` → product 35455, brand 1  
3. Request at `2020-06-14 21:00` → product 35455, brand 1  
4. Request at `2020-06-15 10:00` → product 35455, brand 1  
5. Request at `2020-06-16 21:00` → product 35455, brand 1  

You can import this collection into Postman and execute the requests directly against the running application.


---