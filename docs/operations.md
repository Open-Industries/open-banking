# Operations (summary)

See [`operations/runbook.md`](operations/runbook.md) for deploy/rollback and incident
procedures, and [`operations/slos.md`](operations/slos.md) for the service level objectives.
Every service exposes `/q/health/live`, `/q/health/ready` and `/q/metrics`; logs go to
stdout as structured event streams (12-Factor XI).
