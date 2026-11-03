# Customer Service

Bounded context: **Party / Customer**. Owns customer identity data, onboarding and KYC status.

| Item | Value |
|---|---|
| Port | `8081` (`HTTP_PORT`) |
| Base path | `/api/v1/customers` |
| Owns data | Customer, KYC status |
| Publishes | `customer.onboarded`, `customer.kyc-decided` (CloudEvents, see `contracts/asyncapi.yaml`) |

Run locally: `mvn -pl services/customer-service quarkus:dev` — OpenAPI at `/docs`, health at `/q/health`, metrics at `/q/metrics`.

Design rules that apply here: hexagonal ports and adapters, database-per-service, RFC 9457 errors, money/data standards in `docs/`. See the Architecture Description (`docs/architecture/architecture-description.md`).
