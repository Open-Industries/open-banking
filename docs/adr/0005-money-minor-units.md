# ADR-0005: Money as integer minor units with ISO 4217 currency codes

| Field | Value |
|---|---|
| Status | Accepted |
| Date | 2026-11-03 |
| Standard traceability | ISO/IEC/IEEE 42010 (architecture decision record) |

## Context

Floating point cannot represent decimal money exactly; minor units are exact, auditable and
match card/rail message formats.

## Decision

Amounts are `long` minor units plus an ISO 4217 alpha-3 currency. Rates are basis points.
Floating-point money is forbidden in code review and in the API standard.

## Consequences

Positive: exact arithmetic; deterministic rounding (final installment absorbs it).
Negative: integrators must convert at the edge — documented in the API standard.
