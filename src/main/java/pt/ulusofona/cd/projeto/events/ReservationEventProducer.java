package pt.ulusofona.cd.projeto.events;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pt.ulusofona.cd.projeto.dto.MessageEnvelope;
import pt.ulusofona.cd.projeto.dto.NotificationResponse;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${notification.events.restaurant-notified-events}")
    private String restaurantNotifiedTopic;

    public void sendRestaurantNotified(NotificationResponse payload) {
        MessageEnvelope<NotificationResponse> envelope = new MessageEnvelope<>(
                UUID.randomUUID(),                // messageId
                "ReservationCreated",             // type
                Instant.now(),                    // timestamp
                payload.getId(),                  // correlationId (use reservation ID for traceability)
                "reservation-service:create",     // causationId (originating operation)
                payload                           // payload (the actual reservation data)
        );

        kafkaTemplate.send(restaurantNotifiedTopic, payload.getId().toString(), envelope)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("Published restaurant notified event for notification: " + payload.getId());
                    } else {
                        System.err.println("Failed to publish restaurant notified event: " + ex.getMessage());
                    }
                });
    }
}
