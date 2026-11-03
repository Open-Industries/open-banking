package org.openindustries.banking.card;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CardService {

    @Inject
    CardRepository repository;

    public Card issue(IssueCardRequest request) {
        String brand = request.brand() == null ? "GENERIC" : request.brand();
        return repository.save(Card.issue(request.accountId(), brand));
    }

    public Card get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "Card not found: " + id));
    }

    public List<Card> byAccount(UUID accountId) {
        return repository.findByAccount(accountId);
    }

    public Card block(UUID id) {
        Card card = get(id);
        if ("CANCELLED".equals(card.status)) {
            throw new DomainException(409, "A cancelled card cannot be blocked");
        }
        card.status = "BLOCKED";
        return repository.save(card);
    }
}
