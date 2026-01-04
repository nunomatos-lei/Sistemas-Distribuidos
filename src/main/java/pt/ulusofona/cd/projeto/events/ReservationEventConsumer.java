package pt.ulusofona.cd.projeto.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import pt.ulusofona.cd.projeto.dto.MessageEnvelope;
import pt.ulusofona.cd.projeto.dto.ReservationPayload;
import pt.ulusofona.cd.projeto.service.NotificationService;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationEventConsumer {

    private final NotificationService service;
    private final ObjectMapper objectMapper;

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 3000, multiplier = 2.0),
            dltTopicSuffix = ".DLT"
    )
    @KafkaListener(
            topics = {
                    "${reservation.events.reservation-created-events}",
                    "${reservation.events.reservation-canceled-events}",
                    "${reservation.events.reservation-confirmed-events}"
            },
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onReservationChange(MessageEnvelope<Object> envelope) {
        try {
            log.info("Received envelope: {}", envelope);
            ReservationPayload payload = objectMapper.convertValue(envelope.getPayload(), ReservationPayload.class);

            log.info("Processed message: {} for reservation {}", envelope.getType(), payload.getId());
            log.info("Correlation ID: {}", envelope.getCorrelationId());
            log.info("Timestamp: {}", envelope.getTimestamp());


            if (envelope.getPayload() == null) {
                log.warn("Payload receveid is null.");
                return;
            }
            String reservationId = payload.getId().toString();


            if (reservationId.startsWith("FAIL")) {
                throw new RuntimeException("Simulated failure for " + reservationId);
            }

            log.info("Processing envelope: {}", envelope);

            // creates a notification
            service.createNotificationConsumer(payload, envelope.getType());

        } catch (Exception e) {
            log.error("Error processing envelope: {}", envelope, e);
            throw e;
        }
    }
    @DltHandler
    public void handleDlt(MessageEnvelope<Object> envelope) {
        try {
            log.error("Message moved to DLT: {}", envelope);

            Object rawPayload = envelope.getPayload();
            ReservationPayload payload = null;

            if (rawPayload != null) {
                try {
                    payload = objectMapper.convertValue(rawPayload, ReservationPayload.class);
                } catch (IllegalArgumentException ex) {
                    log.error("It was not possible to convert the DLT payload to ReservationPayload.", ex);
                }
            }

            if (payload != null) {
                log.error("DLT - Permanent Failure in Reservation ID: {}", payload.getId());
            } else {
                log.error("DLT - Payload not readable or null.");
            }

        } catch (Exception e) {
            log.error("Error in DLT handler logic", e);
        }
    }
}