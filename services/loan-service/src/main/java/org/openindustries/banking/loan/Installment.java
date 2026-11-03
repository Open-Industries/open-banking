package org.openindustries.banking.loan;

/** One row of an amortization schedule (French system: constant payment). */
public record Installment(int number, long paymentMinor, long principalMinor, long interestMinor, long balanceAfterMinor) {
}
