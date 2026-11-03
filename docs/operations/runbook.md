# Operations Runbook

## Deploy (per environment)

1. Confirm the release tag and image digests in the release record.
2. `kubectl apply -k deploy/kubernetes/overlays/<env>` (services) and
   `kubectl apply -f deploy/knative/` (functions).
3. Verify: all Deployments ready, `/q/health/ready` green via the gateway, SLO dashboard nominal.
4. Rollback: re-apply the previous overlay (images are immutable and promoted, never rebuilt).

## Health endpoints (every service)

- Liveness: `GET /q/health/live` — Readiness: `GET /q/health/ready`
- Metrics: `GET /q/metrics` (Prometheus) — API docs: `/docs`, contract: `/openapi`

## Common incidents

| Symptom | First checks | Action |
|---|---|---|
| Payment stuck in AUTHORIZED | Ledger-service health; fraud function errors | If the ledger is healthy, re-drive execution (idempotent); else mark FAILED and compensate |
| Balance read model drift | Trial balance vs account balances job output | Replay `ledger.posting-applied` events for the account; never edit balances by hand |
| Function not triggering | Knative Broker delivery metrics; trigger filters | Check the trigger's event type against `contracts/asyncapi.yaml` |
| Error rate spike after deploy | Correlation ids in logs; changed contract diff | Roll back first, diagnose second (G4 rule) |

## Admin processes (12-Factor XII)

Schema migrations, idempotency-key purge and statement regeneration run as one-off Jobs or
function invocations using the **same image and configuration** as the service — never as
interactive sessions inside a production pod.
