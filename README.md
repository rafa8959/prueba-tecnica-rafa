# Winbou Prices API

This project implements an API-first approach using OpenAPI specification.

---

## 📑 API First

The API contract is defined in [`openapi.yaml`](./src/main/resources/openapi.yaml).

- The API is designed first and code is generated/implemented against this contract.
- You can explore the endpoints using Swagger UI once the application is running:

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


## 🧪 Tests

Run domain unit tests with:
```bash
mvn test
```

---