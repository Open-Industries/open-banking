package org.openindustries.banking.customer;

import java.time.Instant;
import java.util.UUID;

/**
 * Customer aggregate root (Party pattern).
 * Personal data is minimized (ISO/IEC 27001 A.5.34 / privacy) and never logged.
 */
public class Customer {
    public UUID id;
    public String type;          // INDIVIDUAL | BUSINESS
    public String fullName;
    public String documentId;    // national id / tax id — sensitive, store encrypted at rest
    public String email;
    public String kycStatus;     // PENDING | VERIFIED | REJECTED
    public String status;        // ACTIVE | BLOCKED | CLOSED
    public Instant createdAt;

    public static Customer create(String type, String fullName, String documentId, String email) {
        Customer c = new Customer();
        c.id = UUID.randomUUID();
        c.type = type;
        c.fullName = fullName;
        c.documentId = documentId;
        c.email = email;
        c.kycStatus = "PENDING";
        c.status = "ACTIVE";
        c.createdAt = Instant.now();
        return c;
    }
}
