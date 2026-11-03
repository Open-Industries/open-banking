package org.openindustries.banking.notification;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class NotificationService {

    private static final Logger LOG = Logger.getLogger(NotificationService.class);

    @Inject
    NotificationRepository repository;

    /**
     * Dispatches a notification. The reference adapter logs the dispatch (recipient
     * redacted); production adapters call the configured email/SMS/push provider.
     * In the event-driven deployment this same operation is invoked by CloudEvents
     * from the domain topics (see contracts/asyncapi.yaml).
     */
    public Notification dispatch(SendNotificationRequest request) {
        Notification notification = repository.save(
                Notification.create(request.channel(), request.recipient(), request.template()));
        LOG.infof("Dispatching notification %s via %s (template %s)",
                notification.id, notification.channel, notification.template);
        notification.status = "SENT";
        return repository.save(notification);
    }

    public Notification get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "Notification not found: " + id));
    }

    public List<Notification> list() {
        return repository.findAll();
    }
}
