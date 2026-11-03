package org.openindustries.banking.loan;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class LoanRepository {
    private final ConcurrentHashMap<UUID, Loan> store = new ConcurrentHashMap<>();

    public Loan save(Loan loan) {
        store.put(loan.id, loan);
        return loan;
    }

    public Optional<Loan> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Loan> findByCustomer(UUID customerId) {
        return store.values().stream().filter(l -> l.customerId.equals(customerId)).toList();
    }
}
