package org.openindustries.banking.payment;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class PaymentRepository {
    private final ConcurrentHashMap<UUID, PaymentOrder> store = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, UUID> byIdempotencyKey = new ConcurrentHashMap<>();

    public PaymentOrder save(PaymentOrder order) {
        store.put(order.id, order);
        byIdempotencyKey.put(order.idempotencyKey, order.id);
        return order;
    }

    public Optional<PaymentOrder> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<PaymentOrder> findByIdempotencyKey(String key) {
        UUID id = byIdempotencyKey.get(key);
        return id == null ? Optional.empty() : findById(id);
    }

    public List<PaymentOrder> findAll() {
        return List.copyOf(store.values());
    }
}
