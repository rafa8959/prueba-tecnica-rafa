# Winbou Prices API

This project implements an API-first approach using OpenAPI specification.

---

## 📑 API First

The API contract is defined in [`openapi.yaml`](./openapi.yaml).

- The API is designed first and code is generated/implemented against this contract.
- You can explore the endpoints using Swagger UI once the application is running:

## Domain layer

The domain layer was implemented following hexagonal architecture and DDD principles:
- `Price` aggregate with validation rules
- Domain exceptions (`InvalidPriceException`, `PriceNotFoundException`)
- `PriceRepository` port abstraction
- `PriceDomainService` encapsulating business logic (select applicable price by priority)

This layer is fully independent of infrastructure and frameworks.


## 🧪 Tests

Run domain unit tests with:
```bash
mvn test
```

---