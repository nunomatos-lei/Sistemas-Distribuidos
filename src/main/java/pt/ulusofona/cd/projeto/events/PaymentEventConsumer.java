package pt.ulusofona.cd.projeto.events;

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

    private final MessageEnvelopeConverter messageConverter;
    private final ReservationRepository repository;
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
    public void onReservationChange(String rawMessage) {
        try {
            log.info("Received raw message: {}", rawMessage);

            MessageEnvelope<PaymentPayload> message = messageConverter.convertFromJson(rawMessage, PaymentPayload.class);

            String reservationId = message.getPayload().getId().toString();
            if (reservationId.startsWith("FAIL")) {
                throw new RuntimeException("Simulated failure for " + reservationId);
            }

            // updates a reservation
            service.confirmReservation(message.getPayload().getReservationId());

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
                MessageEnvelope<PaymentPayload> message = messageConverter.convertFromJson(rawMessage, PaymentPayload.class);
                log.error("DLT - Payment ID: {}", message.getPayload().getId());
            } catch (Exception e) {
                log.error("Could not extract Payment ID from DLT message", e);
            }
        } catch (Exception e) {
            log.error("Error in DLT handler", e);
        }
    }
}
