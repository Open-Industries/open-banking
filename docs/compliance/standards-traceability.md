# Standards Traceability Matrix

Every standard claimed by this platform, and where the conformance evidence lives.
Reviewed at each release (life-cycle gate G3).

| Standard | Requirement area | Evidence in this repository |
|---|---|---|
| ISO/IEC/IEEE 42010 | Architecture description structure | `docs/architecture/architecture-description.md` (stakeholders, concerns, viewpoints, views, decisions, correspondences) |
| ISO/IEC/IEEE 42010 | Architecture decisions recorded | `docs/adr/` (ADR-0001…0010) |
| ISO/IEC 25010 | Product quality model | `docs/architecture/quality-attributes.md` (characteristics + scenarios QS-01…QS-10) |
| ISO/IEC 25010 | Quality evidence | Tests per service (`mvn verify`), SLOs (`docs/operations/slos.md`) |
| ISO/IEC/IEEE 15288 | System life-cycle processes | `docs/lifecycle/software-lifecycle.md` §2 mapping |
| ISO/IEC/IEEE 12207 | Software life-cycle processes | Same mapping; implementation/verification rows |
| ISO/IEC 27001 | ISMS technical evidence | `docs/security/` document set; logs, ADRs, CI records as clause-9 evidence |
| ISO/IEC 27002 | Controls implementation | `docs/security/iso-27001-27002-control-mapping.md` |
| Twelve-Factor App | Cloud application methodology | `docs/architecture/twelve-factor.md` factor-by-factor evidence |
| OpenAPI 3.1 / AsyncAPI 2.6 | Interface contracts | `contracts/openapi.yaml`, `contracts/asyncapi.yaml` |
| CloudEvents 1.0 | Event envelope | `contracts/asyncapi.yaml`; API standards doc |
| RFC 9457 | Error format | `Problem.java` in every service; `docs/api/api-standards.md` |
| ISO 4217 | Currency codes | Validation patterns in request DTOs; data architecture §2 |
| ISO 20022 (alignment) | Payment semantics | Data architecture §2; rail adapter design in Payment context |
| PCI DSS (scope reduction) | Cardholder data | Masked PAN only (`card-service`); tokenization externalized |
