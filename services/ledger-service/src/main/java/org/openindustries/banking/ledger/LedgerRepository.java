package org.openindustries.banking.ledger;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Append-only outbound port; production adapter is JPA on the ledger's own database with insert-only grants. */
@ApplicationScoped
public class LedgerRepository {
    private final ConcurrentHashMap<UUID, JournalEntry> store = new ConcurrentHashMap<>();

    public JournalEntry append(JournalEntry entry) {
        store.put(entry.id, entry);
        return entry;
    }

    public Optional<JournalEntry> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<JournalEntry> findByReference(String reference) {
        return store.values().stream().filter(e -> e.reference.equals(reference)).findFirst();
    }

    public List<JournalEntry> findAll() {
        return List.copyOf(store.values());
    }

    /** Trial balance per account: credits minus debits, in minor units. */
    public long balanceOf(UUID accountId, String currency) {
        long balance = 0;
        for (JournalEntry entry : store.values()) {
            for (JournalEntry.Posting p : entry.postings) {
                if (p.accountId().equals(accountId) && p.currency().equals(currency)) {
                    balance += "CREDIT".equals(p.direction()) ? p.amountMinor() : -p.amountMinor();
                }
            }
        }
        return balance;
    }
}
