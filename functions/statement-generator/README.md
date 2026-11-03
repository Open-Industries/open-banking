# Statement Generator (serverless function)

Quarkus Funqy function deployed on Knative / OpenShift Serverless (scale to zero).
Summarizes one account's postings for a period into statement totals per currency.

Invoke: `POST /statementGenerator` with a `StatementRequest` (see `contracts/asyncapi.yaml`).
