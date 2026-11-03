# Twelve-Factor Alignment

Binding application standard (ADR-0010). Each factor of
[The Twelve-Factor App](https://12factor.net) mapped to how this platform implements and
verifies it.

| # | Factor | Implementation in this platform | Evidence in the repository |
|---|---|---|---|
| I | **Codebase** | One repository, one Maven reactor; services and functions are modules of the same codebase, deployed independently | Root `pom.xml` modules; this repo |
| II | **Dependencies** | Every dependency declared explicitly (Quarkus BOM-managed); no reliance on system-installed packages; containers carry their runtime | `pom.xml` files; `src/main/docker/Dockerfile.jvm` per service |
| III | **Config** | All configuration from environment variables (`HTTP_PORT`, `DB_URL`, `OIDC_ISSUER`, `LOG_LEVEL`, …); secrets injected by the platform, never committed | `application.properties` uses `${ENV:default}` placeholders only; `.env.example` lists names without values |
| IV | **Backing services** | Databases, Kafka/Knative Broker and providers are attached resources, swapped by config (in-memory ↔ JPA adapter; INTERNAL ↔ SEPA rail) | Repository ports/adapters in every service; `deploy/` manifests reference services by URL/secret |
| V | **Build, release, run** | CI builds immutable images once and promotes them across environments; releases are image tag + config, strictly separated from the build | CI workflow; `deploy/` overlays per environment |
| VI | **Processes** | Services are stateless; state lives in their own database or arrives in the request (functions are pure input→output) | No in-JVM session state; in-memory adapters are reference-only and hold no cross-request truth in production |
| VII | **Port binding** | Each service exports HTTP via its own embedded server on `HTTP_PORT` (8081–8087; functions on 8080) | `quarkus.http.port` in every `application.properties` |
| VIII | **Concurrency** | Scale by adding process replicas (Deployments + HPA) and by function instances (Knative), never by vertical-only tuning | HPA in `deploy/kubernetes/`; Knative scaling in `deploy/knative/` |
| IX | **Disposability** | Fast startup (Quarkus), graceful shutdown, idempotent consumers make stops and restarts safe | Health probes (`/q/health/live`, `/q/health/ready`) in manifests; idempotency rules in API standard |
| X | **Dev/prod parity** | Same images and topology in every environment; local dependencies via `docker-compose.yml` mirroring production services | `docker-compose.yml`; environment overlays differ only in config/scale |
| XI | **Logs** | Logs are event streams written to stdout (structured in production), never to local files; the platform aggregates them | `quarkus.log` settings; no file appenders anywhere |
| XII | **Admin processes** | One-off tasks (schema migration, backfills, statement regeneration) run as Jobs/functions against the same image and config, not as ad-hoc shell sessions | Documented in `../operations/runbook.md`; migrations via Flyway in the JPA adapter profile |

Non-compliance with any factor in a change is an architecture review finding (see the
life-cycle gates in `../lifecycle/software-lifecycle.md`).
