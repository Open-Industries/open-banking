# Account Service

Bounded context: **Deposit Accounts**. Owns current/savings accounts. Balances exposed here are a
read model; the **Ledger Service is the system of record** for money movement.

| Item | Value |
|---|---|
| Port | `8082` (`HTTP_PORT`) |
| Base path | `/api/v1/accounts` |
| Owns data | Account, account status |
| Consumes | `ledger.posting-applied` (updates the balance read model) |
| Publishes | `account.opened`, `account.blocked` |

Run locally: `mvn -pl services/account-service quarkus:dev`.
