package org.openindustries.banking.payment;

import java.time.Instant;
import java.util.UUID;

/**
 * Payment order aggregate. Rail-agnostic: the same order can settle internally
 * (book transfer in the ledger) or through an external rail adapter (SEPA, FedNow,
 * SPEI, …) selected by configuration — the domain never changes (adapter pattern).
 * Lifecycle: CREATED -> AUTHORIZED -> EXECUTED | REJECTED | FAILED.
 */
public class PaymentOrder {
    public UUID id;
    public String idempotencyKey;
    public UUID debitAccountId;
    public UUID creditAccountId;
    public long amountMinor;
    public String currency;   // ISO 4217
    public String rail;       // INTERNAL | SEPA | FEDNOW | SPEI | GENERIC
    public String status;
    public Instant createdAt;

    public static PaymentOrder create(String key, UUID debit, UUID credit, long amount, String currency, String rail) {
        PaymentOrder o = new PaymentOrder();
        o.id = UUID.randomUUID();
        o.idempotencyKey = key;
        o.debitAccountId = debit;
        o.creditAccountId = credit;
        o.amountMinor = amount;
        o.currency = currency;
        o.rail = rail;
        o.status = "CREATED";
        o.createdAt = Instant.now();
        return o;
    }
}
