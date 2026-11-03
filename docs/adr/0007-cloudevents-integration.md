# ADR-0007: Event-driven integration with CloudEvents; synchronous only where required

| Field | Value |
|---|---|
| Status | Accepted |
| Date | 2026-11-03 |
| Standard traceability | ISO/IEC/IEEE 42010 (architecture decision record) |

## Context

Asynchrony decouples availability and lets functions consume the same streams; CloudEvents
keeps the envelope standard across transports.

## Decision

Services integrate asynchronously via CloudEvents on the Knative Broker (Kafka-backed in
production). Synchronous REST is reserved for interactions needing an immediate business
answer (payment creation, ledger booking, KYC verdict).

## Consequences

Positive: resilience, replay, scale-to-zero consumers. Negative: eventual consistency —
made explicit in the domain view and in API responses (read models are labelled).
