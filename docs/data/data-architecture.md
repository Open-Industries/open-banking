# Data Architecture

Data view (VP-5) of the Architecture Description.

## 1. Ownership

| Data | Owner (only writer) | Store (reference → production) |
|---|---|---|
| Customers, KYC status | customer-service | in-memory → PostgreSQL `customers` |
| Accounts, balance read model | account-service | in-memory → PostgreSQL `accounts` |
| Journal entries & postings | ledger-service | in-memory → PostgreSQL `ledger` (insert-only grants) |
| Payment orders | payment-service | in-memory → PostgreSQL `payments` |
| Cards (masked) | card-service | in-memory → PostgreSQL `cards` |
| Loans & schedules | loan-service | in-memory → PostgreSQL `loans` |
| Notifications trail | notification-service | in-memory → PostgreSQL `notifications` |

The in-memory adapter exists so the platform runs anywhere with zero setup (evaluation,
tests, CI). Selecting production persistence is configuration (`DB_KIND`, `DB_URL`) plus the
JPA adapter — the domain and the contracts never change (12-Factor IV).

## 2. Money and reference data

- Amounts: **integer minor units** + **ISO 4217** alpha-3 currency code (ADR-0005).
- Rates: annual **basis points**. Rounding: half-up at computation boundaries; amortization
  schedules absorb rounding in the final installment so they close at exactly zero.
- Payment semantics align with **ISO 20022** concepts (debtor/creditor accounts, remittance
  reference, end-to-end id = the payment's idempotency key) so rail adapters translate
  without information loss.

## 3. The ledger

Append-only. A journal entry is the unit of atomicity; its postings must balance per
currency or nothing persists. Corrections are compensating entries referencing the original.
Trial balance (`GET /api/v1/ledger/balances`) is the reconciliation source; account read
models are reconciled against it by a scheduled job — discrepancies alert, never auto-fix.

## 4. Consistency

Within a context: strong (single aggregate transactions). Across contexts: eventual, via
CloudEvents with idempotent consumers and per-aggregate ordering. Multi-step flows use
sagas with explicit compensation (payment execution failure → FAILED + compensating entry).

## 5. Retention, privacy, erasure

- Ledger entries: retained per the adopter's regulatory schedule (typically ≥ 10 years);
  they contain no PII beyond account ids.
- Customer PII: erasable per data-subject rights — erasure tombstones the customer record
  while ledger integrity is preserved by keeping account ids pseudonymous.
- Idempotency keys: retained 30 days (configurable), then purged by an admin job
  (12-Factor XII).
- Backups: per-service, encrypted, restore tested quarterly (ISO 27002 A.8.13).
