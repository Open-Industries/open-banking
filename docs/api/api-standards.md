# API Standards

Binding for every public interface of the platform (ADR-0006).

## REST

- **Resources, not verbs**: plural nouns (`/api/v1/payments`), actions as sub-resources
  (`POST /api/v1/payments/{id}/execute`) only for lifecycle transitions.
- **Versioning**: URI major version (`/api/v1`); backwards-compatible changes within a
  version; a breaking change ships a new major version and keeps the old one for a
  published deprecation window.
- **Methods & codes**: `POST` create → `201`; async dispatch → `202`; validation → `400`;
  domain rule violation → `422`; illegal state transition → `409`; not found → `404`.
- **Errors**: RFC 9457 `application/problem+json` — `type`, `title`, `status`, `detail`.
  Never stack traces, never internal ids of other contexts.
- **Idempotency**: every create accepts `Idempotency-Key` (payments require it); the same
  key + same payload returns the original result; the same key + different payload → `409`.
- **Pagination**: `?limit` (default 50, max 200) and `?cursor`; responses carry the items
  only — totals are a separate, explicit endpoint when needed.
- **Filtering**: query parameters on the owning attribute only; no cross-context joins.
- **Naming & types**: JSON field names are `camelCase`; money is always the pair
  `amountMinor` (integer) + `currency` (ISO 4217 alpha-3); timestamps are RFC 3339 UTC;
  ids are UUIDs.
- **Security**: OAuth 2.1 bearer tokens validated at the gateway; scopes per context
  (`customers.read`, `payments.write`, …); every call carries `X-Correlation-Id`.

## Events

- **Envelope**: CloudEvents 1.0 (`specversion`, `id`, `source`, `type`, `time`, `subject`,
  `data`). Type naming: `<context>.<event>` in past tense (`payment.executed`).
- **Compatibility**: additive changes only within a major event version; consumers are
  idempotent and tolerate unknown fields; ordering is guaranteed only per aggregate id
  (`subject`).
- **Catalogue**: `contracts/asyncapi.yaml` is the registry of record (correspondence CR-3).

## Contract governance

OpenAPI is generated from code (`quarkus-smallrye-openapi`) and diffed against `contracts/`
in CI — drift fails the build. Consumer-driven contract tests run for every cross-context
synchronous call.
