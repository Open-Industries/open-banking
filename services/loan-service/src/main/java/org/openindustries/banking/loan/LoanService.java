package org.openindustries.banking.loan;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class LoanService {

    @Inject
    LoanRepository repository;

    public Loan apply(ApplyLoanRequest request) {
        return repository.save(Loan.apply(request.customerId(), request.principalMinor(),
                request.currency(), request.termMonths(), request.annualRateBps()));
    }

    public Loan get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "Loan not found: " + id));
    }

    public List<Loan> byCustomer(UUID customerId) {
        return repository.findByCustomer(customerId);
    }

    public Loan decide(UUID id, boolean approved) {
        Loan loan = get(id);
        if (!"APPLIED".equals(loan.status)) {
            throw new DomainException(409, "Only an APPLIED loan can be decided");
        }
        loan.status = approved ? "APPROVED" : "REJECTED";
        return repository.save(loan);
    }

    /** French amortization schedule computed with integer minor units (final row absorbs rounding). */
    public List<Installment> schedule(UUID id) {
        Loan loan = get(id);
        double monthlyRate = loan.annualRateBps / 10_000.0 / 12.0;
        long payment;
        if (monthlyRate == 0) {
            payment = loan.principalMinor / loan.termMonths;
        } else {
            double factor = Math.pow(1 + monthlyRate, loan.termMonths);
            payment = Math.round(loan.principalMinor * monthlyRate * factor / (factor - 1));
        }
        List<Installment> rows = new ArrayList<>();
        long balance = loan.principalMinor;
        for (int n = 1; n <= loan.termMonths; n++) {
            long interest = Math.round(balance * monthlyRate);
            long principal = (n == loan.termMonths) ? balance : payment - interest;
            long actualPayment = principal + interest;
            balance -= principal;
            rows.add(new Installment(n, actualPayment, principal, interest, balance));
        }
        return rows;
    }
}
