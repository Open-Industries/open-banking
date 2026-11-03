# Payment Service

Bounded context: **Payments**. Rail-agnostic payment orders with an explicit lifecycle
(`CREATED -> AUTHORIZED -> EXECUTED`) and mandatory `Idempotency-Key` on creation.

| Item | Value |
|---|---|
| Port | `8084` (`HTTP_PORT`) |
| Base path | `/api/v1/payments` |
| Owns data | Payment orders |
| Calls | Ledger Service (booking), rail adapters (SEPA/FedNow/SPEI/GENERIC by configuration) |
| Publishes | `payment.authorized`, `payment.executed`, `payment.failed` |
