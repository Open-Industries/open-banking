package org.openindustries.banking.ledger;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.UUID;

public record CreateJournalEntryRequest(
        @NotBlank String reference,
        String description,
        @NotEmpty @Valid List<PostingRequest> postings) {

    public record PostingRequest(
            UUID accountId,
            @NotBlank @Pattern(regexp = "DEBIT|CREDIT") String direction,
            @Positive long amountMinor,
            @NotBlank @Pattern(regexp = "[A-Z]{3}") String currency) {
    }
}
