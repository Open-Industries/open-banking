package org.openindustries.banking.payment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CreatePaymentRequest(
        @NotNull UUID debitAccountId,
        @NotNull UUID creditAccountId,
        @Positive long amountMinor,
        @NotBlank @Pattern(regexp = "[A-Z]{3}") String currency,
        @Pattern(regexp = "INTERNAL|SEPA|FEDNOW|SPEI|GENERIC") String rail) {
}
