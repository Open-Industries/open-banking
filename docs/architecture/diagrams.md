# Architecture Diagrams

All diagrams are Mermaid, rendered natively by GitHub. They are views governed by the
viewpoints defined in `architecture-description.md` (ISO/IEC/IEEE 42010).

## D-1 — System Context (C4 Level 1)

```mermaid
flowchart TB
    CUST[Bank customers] --> CH[Digital channels<br/>web / mobile / branch systems]
    OPS[Bank operations & back office] --> CH
    CH --> OB[Open Banking<br/>Reference Core Banking Platform]
    PARTNER[Partner APIs / Open Banking consumers] --> OB
    OB --> IDP[Identity provider<br/>OIDC / OAuth 2.1]
    OB --> RAILS[Payment rails<br/>SEPA - FedNow - SPEI - generic adapter]
    OB --> VAULT[Card tokenization vault<br/>PCI-certified, external]
    OB --> NOTIF[Notification providers<br/>email - SMS - push]
    OB --> REG[Reporting & regulatory systems<br/>fed from ledger events]
```

## D-2 — Containers (C4 Level 2)

```mermaid
flowchart LR
    CH[Channels] --> GW[API Gateway]
    GW --> CS[customer-service :8081]
    GW --> AS[account-service :8082]
    GW --> PS[payment-service :8084]
    GW --> KS[card-service :8085]
    GW --> LS[loan-service :8086]
    PS -->|book transfer| LED[ledger-service :8083]
    PS -. CloudEvent .-> FR[fraud-signal-scorer - function]
    CS -. CloudEvent .-> KYC[kyc-document-processor - function]
    LED -. CloudEvent .-> AS
    LED -. period close .-> ST[statement-generator - function]
    CS -. event .-> NT[notification-service :8087]
    PS -. event .-> NT
    LS -. event .-> NT
    CS --> DBC[(db: customers)]
    AS --> DBA[(db: accounts)]
    LED --> DBL[(db: ledger - append-only)]
    PS --> DBP[(db: payments)]
    KS --> DBK[(db: cards)]
    LS --> DBN[(db: loans)]
    NT --> DBT[(db: notifications)]
```

## D-3 — Deployment (Kubernetes / OpenShift + Knative)

```mermaid
flowchart TB
    subgraph PROD[Production namespace - identical topology in every environment]
        IG[Ingress / Gateway] --> SVC[Services: Deployments + HPA<br/>probes, limits, PodDisruptionBudgets]
        BR[Knative Broker - CloudEvents] --> FN[Functions: Knative Services<br/>scale to zero]
        SVC --> DBS[(PostgreSQL per service<br/>managed or operator)]
        SVC -. mTLS via service mesh .-> SVC
    end
    OBS[Observability stack<br/>Prometheus - Loki/ELK - Tempo/Jaeger] -. scrapes & collects .-> PROD
    GIT[Git - this repository] --> CICD[CI/CD pipeline<br/>build once, promote the same image]
    CICD --> PROD
```

## D-4 — Payment execution sequence (internal rail)

```mermaid
sequenceDiagram
    participant CH as Channel
    participant PS as payment-service
    participant FR as fraud-signal-scorer
    participant LED as ledger-service
    participant NT as notification-service
    CH->>PS: POST /payments (Idempotency-Key)
    PS-->>CH: 201 CREATED
    CH->>PS: POST /payments/{id}/authorize
    PS->>FR: score(payment event)
    FR-->>PS: APPROVE / REVIEW / BLOCK
    CH->>PS: POST /payments/{id}/execute
    PS->>LED: POST /journal-entries (balanced DEBIT/CREDIT, reference = payment id)
    LED-->>PS: 201 booked
    PS-->>CH: 200 EXECUTED
    PS-)NT: CloudEvent payment.executed
```

## D-5 — Loan amortization flow

```mermaid
flowchart LR
    A[POST /loans - APPLIED] --> B[POST /loans/id/decision - APPROVED or REJECTED]
    B -->|approved| C[GET /loans/id/schedule<br/>French amortization, minor units]
    C --> D[Disbursement books a journal entry<br/>in the ledger - by reference]
```
