# ADR-0004: Append-only double-entry ledger as the system of record for money

| Field | Value |
|---|---|
| Status | Accepted |
| Date | 2026-11-03 |
| Standard traceability | ISO/IEC/IEEE 42010 (architecture decision record) |

## Context

Double-entry bookkeeping is the centuries-proven correctness model for money; append-only
storage gives the audit trail regulators require (ISO 27001 A.8.15) and makes QS-02/QS-10
verifiable.

## Decision

All money movement is a balanced double-entry journal entry in the Ledger Service.
Entries are never updated or deleted; corrections are compensating entries. Account balances
elsewhere are read models derived from ledger postings.

## Consequences

Positive: provable correctness (trial balance), full auditability. Negative: queries over
raw postings are costlier — mitigated by read models in the Account context.
