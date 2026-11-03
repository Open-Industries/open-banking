# Ledger Service

Bounded context: **General Ledger** — the system of record for money movement.

Rules enforced in code:
- **Double-entry**: every journal entry must balance per currency (debits == credits), otherwise `422`.
- **Append-only**: entries are never updated or deleted; corrections are compensating entries.
- **Idempotent booking**: the caller `reference` books exactly once.
- Amounts are integer minor units (ISO 4217), never floating point.

| Item | Value |
|---|---|
| Port | `8083` (`HTTP_PORT`) |
| Base path | `/api/v1/ledger` |
| Owns data | Journal entries, postings |
| Publishes | `ledger.posting-applied` per posting |
