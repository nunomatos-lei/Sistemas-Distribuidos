package pt.ulusofona.cd.projeto.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import pt.ulusofona.cd.projeto.dto.MessageEnvelope;
import pt.ulusofona.cd.projeto.dto.NotificationRequest;
import pt.ulusofona.cd.projeto.dto.NotificationResponse;
import pt.ulusofona.cd.projeto.dto.ReservationPayload;
import pt.ulusofona.cd.projeto.mapper.NotificationMapper;
import pt.ulusofona.cd.projeto.model.Notification;
import pt.ulusofona.cd.projeto.service.NotificationService;
import pt.ulusofona.cd.projeto.util.MessageEnvelopeConverter;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationEventConsumer {

    private final MessageEnvelopeConverter messageConverter;
    private final NotificationService service;
    private final NotificationEventProducer producer;

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 3000, multiplier = 2.0),
            dltTopicSuffix = ".DLT"
    )
    @KafkaListener(
            topics = {
                    "${reservation.events.reservation-created-events}",
                    "${reservation.events.reservation-canceled-events}",
                    "${payment.events.payment-paid-events}"
            },
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onReservationCreated(String rawMessage) {
        try {
            log.info("Received raw message: {}", rawMessage);

            MessageEnvelope<ReservationPayload> message = messageConverter.convertFromJson(rawMessage, ReservationPayload.class);

            log.info("Processed message: {} for reservation {}", message.getType(), message.getPayload().getId());
            log.info("Correlation ID: {}", message.getCorrelationId());
            log.info("Timestamp: {}", message.getTimestamp());

            String reservationId = message.getPayload().getId().toString();
            if (reservationId.startsWith("FAIL")) {
                throw new RuntimeException("Simulated failure for " + reservationId);
            }

            log.info("Processing Reservation Notified Event for Reservation ID: {}", reservationId);

            // Creates a notification
            NotificationRequest request = new NotificationRequest();
            request.setReservationId(message.getPayload().getId());
            request.setEventType(message.getType());
            request.setRecipient("Restaurant@gmail.com");
            request.setStatus(message.getPayload().getStatus());
            Notification notification = service.createNotification(request);
            NotificationResponse response = NotificationMapper.toResponse(notification);

            // Send notification to kafka
            producer.sendRestaurantNotified(response);
            System.out.println("Email send to: Restaurant@gmail.com");

        } catch (Exception e) {
            log.error("Error processing message: {}", rawMessage, e);
            throw e; // Permite ao mecanismo de retry tratar o erro
        }
    }
    @DltHandler
    public void handleDlt(String rawMessage) {
        try {
            log.error("Message moved to DLT: {}", rawMessage);

            // Tentativa de extração de informação útil para diagnóstico
            try {
                MessageEnvelope<ReservationPayload> message = messageConverter.convertFromJson(rawMessage, ReservationPayload.class);
                log.error("DLT - Notification ID: {}", message.getPayload().getId());
            } catch (Exception e) {
                log.error("Could not extract notification ID from DLT message", e);
            }
        } catch (Exception e) {
            log.error("Error in DLT handler", e);
        }
    }
}
