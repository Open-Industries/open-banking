# Product Quality Model — ISO/IEC 25010 (SQuaRE)

This document applies the **ISO/IEC 25010** product quality model to the platform and turns
each relevant characteristic into measurable quality-attribute scenarios. It is view VP-8
of the Architecture Description (ISO/IEC/IEEE 42010).

## 1. Quality characteristics — applicability and targets

| 25010 characteristic | Sub-characteristics in scope | Platform commitment |
|---|---|---|
| **Functional suitability** | Completeness, correctness, appropriateness | Ledger always balances; payment lifecycle enforced; contract tests per API |
| **Performance efficiency** | Time behaviour, resource utilization, capacity | p95 < 300 ms for reads, < 800 ms for payment creation (reference sizing); Quarkus native/JVM tuning documented per service |
| **Compatibility** | Co-existence, interoperability | OpenAPI 3 / AsyncAPI 2 contracts; ISO 4217 codes; ISO 20022-aligned payment semantics; CloudEvents 1.0 envelope |
| **Usability** *(APIs as the user interface)* | Appropriateness recognizability, learnability, operability | Consistent resource naming, RFC 9457 errors, one pagination/idempotency convention platform-wide |
| **Reliability** | Maturity, availability, fault tolerance, recoverability | SLO 99.9% monthly per service (see `../operations/slos.md`); idempotent retries; health probes; no single point of failure in topology |
| **Security** | Confidentiality, integrity, non-repudiation, accountability, authenticity | Mapped to ISO/IEC 27001/27002 controls in `../security/`; OIDC, mTLS, append-only audit |
| **Maintainability** | Modularity, reusability, analysability, modifiability, testability | One bounded context per service; identical hexagonal layout; ADRs; ≥ unit + contract tests per service |
| **Portability** | Adaptability, installability, replaceability | Twelve-Factor compliance; container-only deployment; config-only adaptation (rails, providers, IdP) |

## 2. Quality-attribute scenarios (measurable)

| ID | Characteristic | Scenario | Target |
|---|---|---|---|
| QS-01 | Reliability | A client retries a payment creation with the same Idempotency-Key after a timeout | Exactly one payment exists; the retry returns the original `201` body |
| QS-02 | Functional correctness | A journal entry whose debits ≠ credits (per currency) is submitted | Rejected with `422` problem+json; nothing is persisted |
| QS-03 | Performance | 200 concurrent balance reads against account-service (reference sizing: 2 replicas, 500 mCPU) | p95 < 300 ms |
| QS-04 | Scalability | Payment traffic triples during a campaign | HPA scales payment-service to its max replicas without error-rate breach; functions scale from zero |
| QS-05 | Modifiability | A new rail (e.g. a national instant-payment scheme) is added | New adapter in payment-service only; no other service changes; ≤ 1 release |
| QS-06 | Portability | The platform is redeployed from Kubernetes to OpenShift (or another region) | Same images, config-only changes; zero code changes |
| QS-07 | Security | A service attempts to read another service's database | Denied by per-service credentials and network policy; attempt is logged |
| QS-08 | Recoverability | A payment-service pod is killed mid-execution | Order remains in a consistent state; retry completes it or compensation marks it FAILED; no double booking in the ledger (same reference) |
| QS-09 | Testability | A developer clones the repository on a clean machine | `mvn verify` runs the whole suite without any external service |
| QS-10 | Auditability | An auditor asks who/what booked journal entry X | Ledger entry carries reference, timestamp and caller identity; trail is immutable |

## 3. Quality-in-use note

Effectiveness, efficiency, satisfaction, freedom from risk and context coverage (ISO 25010
quality-in-use) are properties of each adopting institution's channels and operations; this
platform supports them through correct balances (freedom from economic risk), predictable
APIs (efficiency for integrators) and the operational evidence in `../operations/`.
