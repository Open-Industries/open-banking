package org.openindustries.banking.loan;

import java.time.Instant;
import java.util.UUID;

/**
 * Loan aggregate. Money is integer minor units (ISO 4217); the rate is an annual
 * nominal rate in basis points (1 bp = 0.01%) to keep arithmetic exact and auditable.
 * Lifecycle: APPLIED -> APPROVED -> DISBURSED -> CLOSED | REJECTED.
 */
public class Loan {
    public UUID id;
    public UUID customerId;
    public long principalMinor;
    public String currency;
    public int termMonths;
    public int annualRateBps;
    public String status;
    public Instant createdAt;

    public static Loan apply(UUID customerId, long principalMinor, String currency, int termMonths, int annualRateBps) {
        Loan l = new Loan();
        l.id = UUID.randomUUID();
        l.customerId = customerId;
        l.principalMinor = principalMinor;
        l.currency = currency;
        l.termMonths = termMonths;
        l.annualRateBps = annualRateBps;
        l.status = "APPLIED";
        l.createdAt = Instant.now();
        return l;
    }
}
