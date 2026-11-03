# ISO/IEC 27001 & 27002 — Control Mapping

How this platform implements the ISO/IEC 27002:2022 controls relevant to its architecture,
within an ISMS run per ISO/IEC 27001 (organization-level processes — risk assessment,
Statement of Applicability, internal audit — are the adopter's ISMS duties; this platform
provides the technical evidence). Themes: **5** Organizational, **6** People, **7** Physical,
**8** Technological. Physical controls (theme 7) are inherited from the hosting platform and
are therefore listed as *inherited*.

| ISO 27002 control | Implementation in this platform |
|---|---|
| 5.1 Policies for information security | This `docs/security/` set; security rules are binding architecture standards (ADR-0009) |
| 5.7 Threat intelligence | CI dependency/image scanning; base images rebuilt on CVE feeds |
| 5.15 Access control | OIDC at the edge, mesh workload identity, per-service least-privilege DB credentials, deny-by-default network policies |
| 5.17 Authentication information | Platform secret store only; injected via environment; rotation by the platform; **no secrets in git** (CI secret scanning) |
| 5.19–5.22 Supplier relationships | Rails, IdP, tokenization vault and notification providers sit behind adapters; their security duties are contractual and monitored via health/SLO evidence |
| 5.24–5.28 Incident management | Runbook (`docs/operations/runbook.md`); immutable logs for forensics; post-incident records feed the life-cycle improvement process |
| 5.31 Legal & regulatory requirements | Standards traceability (`docs/compliance/standards-traceability.md`); KYC/AML hooks; data-retention rules in the data architecture |
| 5.34 Privacy and protection of PII | Data minimization (masked PAN, redacted logs), field-level encryption option, retention and erasure procedures per context |
| 5.35 Independent review | Architecture reviews and ADR process; auditors receive this document set as evidence |
| 6.1–6.6 People controls | Adopter's ISMS responsibility; supported technically by SSO+MFA and least privilege so duties are enforceable |
| 7.x Physical controls | **Inherited** from the Kubernetes/OpenShift hosting platform and its data centers |
| 8.2 Privileged access rights | No human write access to production banking data; ledger identity is insert-only; break-glass is platform-audited |
| 8.3 Information access restriction | Database-per-service isolation (CR-4); a service physically cannot read another's schema |
| 8.5 Secure authentication | OAuth 2.1 + PKCE for users; mTLS workload identity for services |
| 8.9 Configuration management | All configuration is versioned (this repo + environment overlays); drift is visible in git |
| 8.13 Information backup | Per-service database backups per the runbook; restore tested as a 12-Factor XII admin process |
| 8.15 Logging | Structured stdout logs, centralized aggregation, correlation ids; the ledger is an append-only audit trail |
| 8.16 Monitoring activities | Prometheus metrics, health probes, SLO alerts (`docs/operations/slos.md`) |
| 8.24 Use of cryptography | TLS 1.2+ in transit, platform encryption at rest, no custom crypto (ADR-0009) |
| 8.25–8.29 Secure development life cycle | The gated life cycle in `docs/lifecycle/software-lifecycle.md`: contract tests, SAST/dependency scans, peer review, signed images |
| 8.31 Separation of environments | Identical topology, separate namespaces/accounts per environment; production data never enters lower environments |
| 8.34 Protection of information systems during audit testing | Audit evidence is produced from logs, ADRs and CI records — no auditor access to live data is required |

Residual risk and control tailoring are recorded by each adopter in its Statement of
Applicability; this mapping is the platform's default baseline.
