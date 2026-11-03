package org.openindustries.banking.loan;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record ApplyLoanRequest(
        @NotNull UUID customerId,
        @Positive long principalMinor,
        @NotBlank @Pattern(regexp = "[A-Z]{3}") String currency,
        @Min(1) @Max(360) int termMonths,
        @Min(0) @Max(10_000) int annualRateBps) {
}
