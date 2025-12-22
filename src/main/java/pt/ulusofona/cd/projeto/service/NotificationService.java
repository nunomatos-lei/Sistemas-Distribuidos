package pt.ulusofona.cd.projeto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulusofona.cd.projeto.dto.NotificationRequest;
import pt.ulusofona.cd.projeto.exception.NotificationNotFoundException;
import pt.ulusofona.cd.projeto.mapper.NotificationMapper;
import pt.ulusofona.cd.projeto.model.Notification;
import pt.ulusofona.cd.projeto.repository.NotificationRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public Notification createNotification(NotificationRequest request) {
        Notification notification = NotificationMapper.toEntity(request);
        return notificationRepository.save(notification);
    }

    public Notification getNotificationById(UUID id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException("Notification not found with id: " + id));
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Transactional
    public Notification updateNotification(UUID id, NotificationRequest notificationDetails) {
        Notification notification = getNotificationById(id);

        notification.setReservationId(notificationDetails.getReservationId());
        notification.setEventType(notificationDetails.getEventType());
        notification.setRecipient(notificationDetails.getRecipient());
        notification.setStatus(notificationDetails.getStatus());

        return notificationRepository.save(notification);
    }

    @Transactional
    public void deleteNotification(UUID id) {
        Notification notification = getNotificationById(id);
        notificationRepository.delete(notification);
    }
}
