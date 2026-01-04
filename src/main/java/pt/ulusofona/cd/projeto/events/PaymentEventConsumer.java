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
import pt.ulusofona.cd.projeto.dto.PaymentPayload;
import pt.ulusofona.cd.projeto.model.Reservation;
import pt.ulusofona.cd.projeto.repository.ReservationRepository;
import pt.ulusofona.cd.projeto.service.ReservationService;
import pt.ulusofona.cd.projeto.util.MessageEnvelopeConverter;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final ObjectMapper objectMapper;
    private final ReservationService service;

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 3000, multiplier = 2.0),
            dltTopicSuffix = ".DLT"
    )
    @KafkaListener(
            topics = {
                    "${payment.events.payment-paid-events}"
            },
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onReservationChange(MessageEnvelope<Object> envelope) {
        try {
            log.info("Received envelope: {}", envelope);
            PaymentPayload payload = objectMapper.convertValue(envelope.getPayload(), PaymentPayload.class);

            log.info("Processed message: {} for payment {}", envelope.getType(), payload.getId());
            log.info("Correlation ID: {}", envelope.getCorrelationId());
            log.info("Timestamp: {}", envelope.getTimestamp());


            if (envelope.getPayload() == null) {
                log.warn("Payload receveid is null.");
                return;
            }
            String paymentId = payload.getId().toString();


            if (paymentId.startsWith("FAIL")) {
                throw new RuntimeException("Simulated failure for " + paymentId);
            }

            log.info("Processing envelope: {}", envelope);

            // creates a notification
            service.confirmReservation(payload.getReservationId());

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
            PaymentPayload payload = null;

            if (rawPayload != null) {
                try {
                    payload = objectMapper.convertValue(rawPayload, PaymentPayload.class);
                } catch (IllegalArgumentException ex) {
                    log.error("It was not possible to convert the DLT payload to PaymentPayload.", ex);
                }
            }

            if (payload != null) {
                log.error("DLT - Permanent Failure in Payment ID: {}", payload.getId());
            } else {
                log.error("DLT - Payload not readable or null.");
            }

        } catch (Exception e) {
            log.error("Error in DLT handler logic", e);
        }
    }
}
