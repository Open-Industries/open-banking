package org.openindustries.banking.card;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Issued card. PCI DSS minimization: the full PAN is NEVER stored or returned by this
 * service — tokenization is delegated to a PCI-certified vault; only the masked PAN and
 * last four digits live here (ISO/IEC 27002 A.8.24 on cryptography, A.5.34 on privacy).
 */
public class Card {
    public UUID id;
    public UUID accountId;
    public String maskedPan;   // **** **** **** 1234
    public String last4;
    public String brand;       // VISA | MASTERCARD | GENERIC
    public String status;      // ACTIVE | BLOCKED | EXPIRED | CANCELLED
    public Instant issuedAt;

    public static Card issue(UUID accountId, String brand) {
        Card c = new Card();
        c.id = UUID.randomUUID();
        c.accountId = accountId;
        c.last4 = String.format("%04d", ThreadLocalRandom.current().nextInt(10_000));
        c.maskedPan = "**** **** **** " + c.last4;
        c.brand = brand;
        c.status = "ACTIVE";
        c.issuedAt = Instant.now();
        return c;
    }
}
