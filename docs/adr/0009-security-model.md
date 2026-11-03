# ADR-0009: Security model: OIDC at the edge, mTLS inside, platform-managed secrets

| Field | Value |
|---|---|
| Status | Accepted |
| Date | 2026-11-03 |
| Standard traceability | ISO/IEC/IEEE 42010 (architecture decision record) |

## Context

Implements ISO/IEC 27001/27002 controls (mapped in `docs/security/`) with the smallest
possible code footprint: no custom cryptography, no secrets in the repository, ever.

## Decision

The API Gateway validates OIDC/OAuth 2.1 tokens; service-to-service traffic is mTLS in the
mesh; each service uses least-privilege credentials; secrets exist only in the platform's
secret store and are injected as environment variables.

## Consequences

Positive: auditable, standard, provider-swappable identity. Negative: local development
needs an IdP — provided by `docker-compose.yml` (Keycloak).
