# Architecture Decision Records (ADRs)

Decisions are part of the Architecture Description (ISO/IEC/IEEE 42010 §5.6). Each ADR is
immutable once accepted; a change is a new ADR that supersedes the old one.

| ADR | Title |
|---|---|
| [0001](0001-microservices-and-serverless.md) | Microservices per bounded context, complemented by serverless functions |
| [0002](0002-java-quarkus-stack.md) | Java 21 and Quarkus as the single implementation stack |
| [0003](0003-database-per-service.md) | Database per service; no shared database, no distributed transactions |
| [0004](0004-double-entry-append-only-ledger.md) | Append-only double-entry ledger as the system of record for money |
| [0005](0005-money-minor-units.md) | Money as integer minor units with ISO 4217 currency codes |
| [0006](0006-api-first-contracts.md) | API-first contracts with RFC 9457 problem details |
| [0007](0007-cloudevents-integration.md) | Event-driven integration with CloudEvents; synchronous only where required |
| [0008](0008-mandatory-idempotency.md) | Idempotency is mandatory on state-creating operations |
| [0009](0009-security-model.md) | Security model: OIDC at the edge, mTLS inside, platform-managed secrets |
| [0010](0010-twelve-factor-binding.md) | Twelve-Factor alignment as a binding application standard |
