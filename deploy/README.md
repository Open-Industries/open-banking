# Deployment

| Path | Target |
|---|---|
| `kubernetes/` | Always-on microservices on Kubernetes/OpenShift: `kubectl apply -k deploy/kubernetes/` |
| `knative/` | Serverless functions on Knative / OpenShift Serverless, plus the Broker triggers that feed them |

Every environment uses the **same manifests and images**; only configuration (env vars,
secrets, replica bounds) changes — 12-Factor X (dev/prod parity) and V (build, release, run).
