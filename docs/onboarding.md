# Developer Onboarding

1. Install Java 21 and Maven 3.9+.
2. Clone the repository and run `mvn verify` — the full suite must pass on a clean machine
   (quality scenario QS-09).
3. Start backing services if needed: `docker compose up -d`.
4. Run a service: `mvn -pl services/ledger-service quarkus:dev`, then open `/docs` for its
   OpenAPI UI and `/q/dev-ui` for the Quarkus Dev UI.
5. Read, in this order: `README.md` → `docs/architecture/architecture-description.md` →
   `docs/architecture/twelve-factor.md` → `docs/api/api-standards.md`.
6. Before your first change: read the ADRs for the context you will touch (`docs/adr/`).
