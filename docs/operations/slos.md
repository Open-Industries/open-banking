# Service Level Objectives

Measured from gateway and service metrics (`/q/metrics`) over rolling 30 days.

| Service | Availability SLO | Latency SLO (p95) | Notes |
|---|---|---|---|
| API Gateway → any service | 99.9% | — | Error budget: ~43 min/month |
| customer-service | 99.9% | reads < 300 ms | |
| account-service | 99.9% | reads < 300 ms | Balance reads are read-model queries |
| ledger-service | 99.95% | booking < 500 ms | Highest criticality: it is the system of record |
| payment-service | 99.9% | create < 800 ms | Execution SLO excludes external rail latency |
| card-service | 99.9% | reads < 300 ms | |
| loan-service | 99.9% | schedule < 500 ms | |
| notification-service | 99.5% | dispatch < 1 s | Provider latency excluded |
| Functions | 99.5% | cold start < 3 s | Scale-to-zero accepted by design |

Breaching an error budget freezes non-essential releases for that context until the budget
recovers (life-cycle gate G5 → G0 feedback).
