# Threat Model (STRIDE summary)

Scope: the platform boundary in `security-architecture.md`. Reviewed at every architecture
change (life-cycle gate G2).

| Threat | Example against this platform | Mitigation in place |
|---|---|---|
| **S**poofing | Forged channel token calling payment-service directly | Gateway-only ingress, OIDC validation, mTLS workload identity, network policies |
| **T**ampering | Altering a booked journal entry to hide theft | Append-only ledger + insert-only DB grants; corrections only as visible compensating entries; trial-balance checks |
| **R**epudiation | Operator denies authorizing a payment | Audit log with actor, timestamp, correlation id and idempotency key on every state change |
| **I**nformation disclosure | PAN or PII leaking via logs or APIs | Masked PAN only; PII never logged (recipients redacted); RFC 9457 errors without internals; field-level encryption option |
| **D**enial of service | Flood against the gateway or a function | Gateway rate limiting, HPA, Knative concurrency limits, per-service resource limits and quotas |
| **E**levation of privilege | A compromised notification-service reading the ledger DB | Database-per-service credentials; a service's credentials open only its own schema (QS-07) |

Out of scope (adopter/platform duties): IdP and cloud account security, PCI certification of
the tokenization vault, endpoint security of channels.
