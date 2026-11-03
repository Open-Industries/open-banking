# ADR-0003: Database per service; no shared database, no distributed transactions

| Field | Value |
|---|---|
| Status | Accepted |
| Date | 2026-11-03 |
| Standard traceability | ISO/IEC/IEEE 42010 (architecture decision record) |

## Context

A shared database is the strongest coupling in enterprise systems and the main enemy of
independent deployability (ISO 25010 modifiability). Correspondence rule CR-4 makes any
cross-context database access an architecture violation.

## Decision

Each microservice owns its database schema and credentials. Cross-service consistency is
achieved with domain events and, where a flow spans services, sagas with compensation —
never with XA/2PC or shared tables.

## Consequences

Positive: autonomy, independent scaling of data, least-privilege credentials (ISO 27002 A.8).
Negative: reporting must be built from events (a dedicated reporting read model), not joins.
