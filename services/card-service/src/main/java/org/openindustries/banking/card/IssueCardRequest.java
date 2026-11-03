package org.openindustries.banking.card;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record IssueCardRequest(
        @NotNull UUID accountId,
        @Pattern(regexp = "VISA|MASTERCARD|GENERIC") String brand) {
}
