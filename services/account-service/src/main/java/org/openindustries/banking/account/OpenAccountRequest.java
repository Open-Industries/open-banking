package org.openindustries.banking.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record OpenAccountRequest(
        @NotNull UUID customerId,
        @NotBlank @Pattern(regexp = "CHECKING|SAVINGS") String type,
        @NotBlank @Pattern(regexp = "[A-Z]{3}") String currency,
        @PositiveOrZero long initialDepositMinor) {
}
