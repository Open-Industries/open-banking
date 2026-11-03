package org.openindustries.banking.notification;

import java.time.Instant;
import java.util.UUID;

/**
 * A dispatched notification. Channel delivery is an outbound adapter concern
 * (email/SMS/push providers are interchangeable); this service owns the request,
 * the template rendering and the delivery status trail for audit.
 */
public class Notification {
    public UUID id;
    public String channel;     // EMAIL | SMS | PUSH
    public String recipient;   // never logged (privacy, ISO/IEC 27002 A.5.34)
    public String template;    // e.g. PAYMENT_EXECUTED, KYC_VERIFIED
    public String status;      // QUEUED | SENT | FAILED
    public Instant createdAt;

    public static Notification create(String channel, String recipient, String template) {
        Notification n = new Notification();
        n.id = UUID.randomUUID();
        n.channel = channel;
        n.recipient = recipient;
        n.template = template;
        n.status = "QUEUED";
        n.createdAt = Instant.now();
        return n;
    }
}
