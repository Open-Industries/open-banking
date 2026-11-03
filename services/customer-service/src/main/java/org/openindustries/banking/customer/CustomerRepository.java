package org.openindustries.banking.customer;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Outbound port (hexagonal architecture). The default adapter is in-memory so the
 * reference implementation runs anywhere; the production adapter is JPA against this
 * service's own database (database-per-service, docs/data/data-architecture.md).
 */
@ApplicationScoped
public class CustomerRepository {
    private final ConcurrentHashMap<UUID, Customer> store = new ConcurrentHashMap<>();

    public Customer save(Customer customer) {
        store.put(customer.id, customer);
        return customer;
    }

    public Optional<Customer> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Customer> findAll() {
        return List.copyOf(store.values());
    }
}
