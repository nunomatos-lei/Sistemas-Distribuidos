package pt.ulusofona.cd.projeto.mapper;

import pt.ulusofona.cd.projeto.dto.NotificationRequest;
import pt.ulusofona.cd.projeto.dto.NotificationResponse;
import pt.ulusofona.cd.projeto.model.Notification;

public class NotificationMapper {
    public static Notification toEntity(NotificationRequest dto) {
        Notification p = new Notification();
        p.setReservationId(dto.getReservationId());
        p.setEventType(dto.getEventType().trim());
        p.setRecipient(dto.getRecipient().trim());
        p.setStatus(dto.getStatus() != null ? dto.getStatus() : "PENDING");
        return p;
    }

    public static NotificationResponse toResponse(Notification entity) {
        NotificationResponse r = new NotificationResponse();
        r.setId(entity.getId());
        r.setReservationId(entity.getReservationId());
        r.setEventType(entity.getEventType());
        r.setRecipient(entity.getRecipient());
        r.setStatus(entity.getStatus());
        r.setSentAt(entity.getSentAt());
        return r;
    }
}
