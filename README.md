# API Contract – Winbou Prices

This branch defines the **API contract** (`openapi.yaml`) for the Winbou Prices service.

## Purpose
- Follow an **API-first** approach: the contract is defined **before** any implementation.
- Ensure that the service implementation, persistence, and tests will be built strictly against this specification.

## Contents
- `src/main/resources/openapi.yaml` → OpenAPI 3.0 specification of the REST endpoint `/prices`.

## Next steps
- Implement the domain and infrastructure layers following hexagonal architecture.
- Expose the REST adapter in compliance with this contract.
- Add exception handling and tests (unit, integration, and e2e) using the defined schema.

---
By committing this contract first, we demonstrate the **API-first methodology** required by the assignment.
