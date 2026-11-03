# Fraud Signal Scorer (serverless function)

Quarkus Funqy function on Knative. Scores payment events with transparent rules and
returns `APPROVE | REVIEW | BLOCK` plus the rationale, for auditability.

Invoke: `POST /fraudSignalScorer` with a `TransactionEvent`.
