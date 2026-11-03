# ADR-0010: Twelve-Factor alignment as a binding application standard

| Field | Value |
|---|---|
| Status | Accepted |
| Date | 2026-11-03 |
| Standard traceability | ISO/IEC/IEEE 42010 (architecture decision record) |

## Context

A *generic, deployable* core banking is only generic if any adopter can build, release and
run it on a compliant platform without modification — exactly what the factors guarantee
(ISO 25010 portability).

## Decision

The Twelve-Factor methodology is adopted in full; `docs/architecture/twelve-factor.md`
maps each factor to its implementation and evidence. Deviations require an ADR superseding
this one.

## Consequences

Positive: portable, operable, reviewable. Negative: some conveniences (local state, file
logs, environment-specific builds) are forbidden by design.
