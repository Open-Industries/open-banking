# ADR-0006: API-first contracts with RFC 9457 problem details

| Field | Value |
|---|---|
| Status | Accepted |
| Date | 2026-11-03 |
| Standard traceability | ISO/IEC/IEEE 42010 (architecture decision record) |

## Context

Contracts are the compatibility promise to channels and partners (ISO 25010 interoperability)
and the testable surface of each service.

## Decision

Public APIs are described by OpenAPI 3 (contracts checked in `contracts/`, generated from
code and diffed in CI); events by AsyncAPI 2 with the CloudEvents 1.0 envelope. All errors
use RFC 9457 `application/problem+json`.

## Consequences

Positive: generated clients, contract tests, predictable integration. Negative: contract
changes require the versioning discipline defined in the API standard.
