# ADR-0008: Idempotency is mandatory on state-creating operations

| Field | Value |
|---|---|
| Status | Accepted |
| Date | 2026-11-03 |
| Standard traceability | ISO/IEC/IEEE 42010 (architecture decision record) |

## Context

Networks fail and clients retry; in banking a duplicate is money. QS-01 and QS-08 depend on
this decision.

## Decision

Payment creation requires an `Idempotency-Key` header; ledger booking is idempotent by
caller `reference`; event consumers must be idempotent. Retries are therefore always safe.

## Consequences

Positive: safe retries end-to-end, simpler clients. Negative: services must store keys
and references (bounded retention, see data architecture).
