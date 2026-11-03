# Deployment (summary)

Deployment view (VP-6): see the Architecture Description and `deploy/`.

- Microservices: `kubectl apply -k deploy/kubernetes/` (Kustomize; Deployments, Services, HPA, probes).
- Functions: `kubectl apply -f deploy/knative/` (Knative Services + Broker triggers, scale to zero).
- Local dependencies: `docker compose up -d` (PostgreSQL, Kafka, Keycloak).
- Releases are immutable images promoted across identical-topology environments (12-Factor V & X).
