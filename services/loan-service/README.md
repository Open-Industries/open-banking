# Loan Service

Bounded context: **Lending**. Origination, decision and French amortization schedules.
Rates are annual basis points; money is integer minor units — no floating point for money,
the schedule's final row absorbs rounding so it always closes at zero.

| Item | Value |
|---|---|
| Port | `8086` (`HTTP_PORT`) |
| Base path | `/api/v1/loans` |
| Owns data | Loans, decisions, schedules |
| Publishes | `loan.applied`, `loan.approved`, `loan.disbursed` |
