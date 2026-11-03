# Services

One independently deployable Quarkus microservice per bounded context. Every service
follows the same hexagonal layout (REST inbound adapter → application service → domain →
repository port), exposes `/openapi`, `/q/health` and `/q/metrics`, returns RFC 9457
problem details, and is configured only through environment variables (Twelve-Factor).

**Implementation order** (see `../docs/implementation-roadmap.md`):
**Ledger first**, then Account, then the Open Banking API surface.

| Service | Port | Bounded context | Step |
|---|---|---|---|
| `ledger-service` | 8083 | General Ledger — double-entry journal, system of record for money | **Step 1 (pilot)** |
| `account-service` | 8082 | Deposit accounts; balances as a read model fed by ledger events | Step 2 |
| `customer-service` | 8081 | Party/customer, onboarding, KYC status | Later |
| `payment-service` | 8084 | Payment orders and rail orchestration | Later |
| `card-service` | 8085 | Cards (masked PAN only) | Later |
| `loan-service` | 8086 | Lending and amortization | Later |
| `notification-service` | 8087 | Channel-agnostic notifications | Later |

Run the pilot service:

```bash
mvn -pl services/ledger-service quarkus:dev
# OpenAPI UI: http://localhost:8083/docs — health: http://localhost:8083/q/health
```
