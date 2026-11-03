package org.openindustries.banking.ledger;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Double-entry journal entry — the heart of the core banking system.
 * Invariant: for every currency, total DEBIT postings == total CREDIT postings.
 * An unbalanced entry is rejected; the ledger is append-only (no updates, no deletes):
 * corrections are new compensating entries (docs/data/data-architecture.md).
 */
public class JournalEntry {
    public UUID id;
    public String reference;       // business reference / idempotency key from the caller
    public String description;
    public List<Posting> postings;
    public Instant bookedAt;

    public record Posting(UUID accountId, String direction, long amountMinor, String currency) {
    }

    public static JournalEntry of(String reference, String description, List<Posting> postings) {
        JournalEntry e = new JournalEntry();
        e.id = UUID.randomUUID();
        e.reference = reference;
        e.description = description;
        e.postings = postings;
        e.bookedAt = Instant.now();
        return e;
    }
}
