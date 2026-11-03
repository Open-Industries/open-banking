package org.openindustries.banking.account;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class AccountRepository {
    private final ConcurrentHashMap<UUID, Account> store = new ConcurrentHashMap<>();

    public Account save(Account account) {
        store.put(account.id, account);
        return account;
    }

    public Optional<Account> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Account> findByCustomer(UUID customerId) {
        return store.values().stream().filter(a -> a.customerId.equals(customerId)).toList();
    }
}
