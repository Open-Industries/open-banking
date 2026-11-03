package org.openindustries.banking.account;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AccountService {

    @Inject
    AccountRepository repository;

    public Account open(OpenAccountRequest request) {
        return repository.save(Account.open(
                request.customerId(), request.type(), request.currency(), request.initialDepositMinor()));
    }

    public Account get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "Account not found: " + id));
    }

    public List<Account> byCustomer(UUID customerId) {
        return repository.findByCustomer(customerId);
    }

    public Account setBlocked(UUID id, boolean blocked) {
        Account account = get(id);
        if ("CLOSED".equals(account.status)) {
            throw new DomainException(409, "A closed account cannot change state");
        }
        account.status = blocked ? "BLOCKED" : "ACTIVE";
        return repository.save(account);
    }

    /** Applies a ledger-confirmed posting to the read-model balance. Called only from ledger events. */
    public Account applyPosting(UUID id, long deltaMinor) {
        Account account = get(id);
        account.balanceMinor += deltaMinor;
        return repository.save(account);
    }
}
