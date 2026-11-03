package org.openindustries.banking.payment;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PaymentService {

    @Inject
    PaymentRepository repository;

    public PaymentOrder create(String idempotencyKey, CreatePaymentRequest request) {
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new DomainException(400, "Idempotency-Key header is required");
        }
        var existing = repository.findByIdempotencyKey(idempotencyKey);
        if (existing.isPresent()) {
            return existing.get(); // safe retry: the payment is created exactly once
        }
        if (request.debitAccountId().equals(request.creditAccountId())) {
            throw new DomainException(422, "Debit and credit accounts must differ");
        }
        String rail = request.rail() == null ? "INTERNAL" : request.rail();
        return repository.save(PaymentOrder.create(idempotencyKey,
                request.debitAccountId(), request.creditAccountId(),
                request.amountMinor(), request.currency(), rail));
    }

    public PaymentOrder get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "Payment order not found: " + id));
    }

    public List<PaymentOrder> list() {
        return repository.findAll();
    }

    public PaymentOrder authorize(UUID id) {
        return transition(id, "CREATED", "AUTHORIZED");
    }

    /**
     * Executes the payment. In production this books the double-entry transfer in the
     * Ledger Service (or submits to the selected rail adapter) and only then marks the
     * order EXECUTED; failures leave it FAILED with a compensating entry if booked.
     */
    public PaymentOrder execute(UUID id) {
        return transition(id, "AUTHORIZED", "EXECUTED");
    }

    private PaymentOrder transition(UUID id, String from, String to) {
        PaymentOrder order = get(id);
        if (!from.equals(order.status)) {
            throw new DomainException(409, "Payment in status " + order.status + " cannot move to " + to);
        }
        order.status = to;
        return repository.save(order);
    }
}
