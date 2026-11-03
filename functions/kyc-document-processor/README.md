# KYC Document Processor (serverless function)

Quarkus Funqy function on Knative. Evaluates KYC submission completeness and returns a
verdict consumed by the Customer Service (`POST /api/v1/customers/{id}/kyc-decision`).

Invoke: `POST /kycDocumentProcessor` with a `KycSubmission`.
