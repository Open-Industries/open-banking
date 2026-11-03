package org.openindustries.banking.ledger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class LedgerService {

    @Inject
    LedgerRepository repository;

    public JournalEntry book(CreateJournalEntryRequest request) {
        // Idempotency: the same caller reference books exactly once (API standard).
        var existing = repository.findByReference(request.reference());
        if (existing.isPresent()) {
            return existing.get();
        }
        Map<String, Long> debits = new HashMap<>();
        Map<String, Long> credits = new HashMap<>();
        for (var p : request.postings()) {
            var target = "DEBIT".equals(p.direction()) ? debits : credits;
            target.merge(p.currency(), p.amountMinor(), Long::sum);
        }
        if (!debits.equals(credits)) {
            throw new DomainException(422,
                    "Unbalanced journal entry: debits " + debits + " != credits " + credits);
        }
        var postings = request.postings().stream()
                .map(p -> new JournalEntry.Posting(p.accountId(), p.direction(), p.amountMinor(), p.currency()))
                .toList();
        return repository.append(JournalEntry.of(request.reference(), request.description(), postings));
    }

    public JournalEntry get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "Journal entry not found: " + id));
    }

    public List<JournalEntry> list() {
        return repository.findAll();
    }

    public long balanceOf(UUID accountId, String currency) {
        return repository.balanceOf(accountId, currency);
    }
}
