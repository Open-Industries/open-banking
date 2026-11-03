# ADR-0002: Java 21 and Quarkus as the single implementation stack

| Field | Value |
|---|---|
| Status | Accepted |
| Date | 2026-11-03 |
| Standard traceability | ISO/IEC/IEEE 42010 (architecture decision record) |

## Context

Type safety and a mature banking ecosystem; Quarkus' fast startup and low memory suit both
always-on services and scale-to-zero functions; a single stack keeps the generic platform
learnable and staffable by adopting institutions.

## Decision

All services and functions are Java 21 on Quarkus 3, built by one Maven reactor.

## Consequences

Positive: one skill set, shared conventions, Dev Services and a strong testing story.
Negative: JVM cold starts for functions are higher than native languages — mitigated by
Quarkus' optimizations and Knative configuration.
