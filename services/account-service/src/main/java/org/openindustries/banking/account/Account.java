package org.openindustries.banking.account;

import java.time.Instant;
import java.util.UUID;

/**
 * Deposit account aggregate.
 * `balanceMinor` is a read-model cache only: the Ledger Service is the system of
 * record for money movement (double-entry). Amounts are integer minor units of an
 * ISO 4217 currency — never floating point (docs/data/data-architecture.md).
 */
public class Account {
    public UUID id;
    public UUID customerId;
    public String type;       // CHECKING | SAVINGS
    public String currency;   // ISO 4217 alpha-3
    public long balanceMinor;
    public String status;     // ACTIVE | BLOCKED | CLOSED
    public Instant openedAt;

    public static Account open(UUID customerId, String type, String currency, long initialDepositMinor) {
        Account a = new Account();
        a.id = UUID.randomUUID();
        a.customerId = customerId;
        a.type = type;
        a.currency = currency;
        a.balanceMinor = initialDepositMinor;
        a.status = "ACTIVE";
        a.openedAt = Instant.now();
        return a;
    }
}
