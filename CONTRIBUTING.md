# Contributing

This project follows the life cycle in `docs/lifecycle/software-lifecycle.md`
(ISO/IEC/IEEE 15288 & 12207) and the architecture in
`docs/architecture/architecture-description.md` (ISO/IEC/IEEE 42010).

Rules for every change:

1. **Contract-first**: update `contracts/` (or the generated OpenAPI) with the code.
2. **Decisions as ADRs**: any architecture-significant change adds or supersedes an ADR in `docs/adr/`.
3. **Tests**: `mvn verify` must pass; new behavior ships with tests (quality scenario QS-09).
4. **Twelve-Factor**: no config in code, no secrets in the repo, logs to stdout only.
5. **Money**: integer minor units only; the ledger stays append-only and balanced.
6. Keep each bounded context autonomous: no cross-service database access, ever (CR-4).

By contributing you agree your work is licensed under Apache-2.0.
