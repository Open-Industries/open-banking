package org.openindustries.banking.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateCustomerRequest(
        @NotBlank @Pattern(regexp = "INDIVIDUAL|BUSINESS") String type,
        @NotBlank String fullName,
        @NotBlank String documentId,
        @NotBlank @Email String email) {
}
