# ADR-0001: Microservices per bounded context, complemented by serverless functions

| Field | Value |
|---|---|
| Status | Accepted |
| Date | 2026-11-03 |
| Standard traceability | ISO/IEC/IEEE 42010 (architecture decision record) |

## Context

Banking capabilities change at different rates and carry different risk profiles; independent
deployability isolates change and blast radius. Functions remove idle cost and operational
surface for event-driven work. One stack (Java/Quarkus) serves both styles.

## Decision

Core capabilities (customers, accounts, ledger, payments, cards, loans, notifications) are
independently deployable Quarkus microservices, one per bounded context. Bursty, periodic or
pure-transform work (KYC checks, fraud scoring, statements) is implemented as Quarkus Funqy
functions on Knative with scale-to-zero.

## Consequences

Positive: independent scaling and releases; clear ownership; zero-cost idle functions.
Negative: distributed-systems complexity (mitigated by contracts, idempotency, observability
standards); eventual consistency between ledger and read models (accepted, see AD §8).
