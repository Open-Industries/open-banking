package org.openindustries.banking.card;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class CardRepository {
    private final ConcurrentHashMap<UUID, Card> store = new ConcurrentHashMap<>();

    public Card save(Card card) {
        store.put(card.id, card);
        return card;
    }

    public Optional<Card> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Card> findByAccount(UUID accountId) {
        return store.values().stream().filter(c -> c.accountId.equals(accountId)).toList();
    }
}
