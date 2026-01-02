package pt.ulusofona.cd.projeto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulusofona.cd.projeto.dto.MessageEnvelope;
import pt.ulusofona.cd.projeto.dto.NotificationRequest;
import pt.ulusofona.cd.projeto.dto.NotificationResponse;
import pt.ulusofona.cd.projeto.dto.ReservationPayload;
import pt.ulusofona.cd.projeto.events.NotificationEventProducer;
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
    private final NotificationEventProducer producer;

    // Post
    @Transactional
    public Notification createNotification(NotificationRequest request) {
        Notification notification = NotificationMapper.toEntity(request);
        return notificationRepository.save(notification);
    }

    @Transactional
    public void createNotificationConsumer(MessageEnvelope<ReservationPayload> message) {
        // Creates a notification
        NotificationRequest request = new NotificationRequest();
        request.setReservationId(message.getPayload().getId());
        request.setEventType(message.getType());
        request.setRecipient(message.getPayload().getCustomerEmail());
        request.setStatus(message.getPayload().getStatus());
        Notification notification = notificationRepository.save(NotificationMapper.toEntity(request));
        NotificationResponse response = NotificationMapper.toResponse(notification);

        // Send notification to kafka
        producer.sendRestaurantNotified(response);
        System.out.println("Email send to: " + message.getPayload().getCustomerEmail());
    }




    // Get
    public Notification getNotificationById(UUID id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException("Notification not found with id: " + id));
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
}
