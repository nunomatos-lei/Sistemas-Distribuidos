package pt.ulusofona.cd.projeto.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import pt.ulusofona.cd.projeto.dto.*;
import pt.ulusofona.cd.projeto.mapper.PaymentMapper;
import pt.ulusofona.cd.projeto.model.Payment;
import pt.ulusofona.cd.projeto.service.PaymentService;
import pt.ulusofona.cd.projeto.util.MessageEnvelopeConverter;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final MessageEnvelopeConverter messageConverter;
    private final PaymentService service;
    private final PaymentEventProducer producer;

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 3000, multiplier = 2.0),
            dltTopicSuffix = ".DLT"
    )
    @KafkaListener(
            topics = {"${reservation.events.reservation-paid-events}"},
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

            PaymentRequest request = new PaymentRequest();
            request.setReservationId(message.getPayload().getId());
            request.setRestaurantId(message.getPayload().getRestaurantId());
            request.setCustomerName(message.getPayload().getCustomerName());
            request.setCustomerEmail(message.getPayload().getCustomerEmail());
            request.setTaxNumber("213424141");
            service.createPayment(request);

            ReservationPayload reservePayload = new ReservationPayload();
            reservePayload.setId(message.getPayload().getId());
            reservePayload.setRestaurantId(message.getPayload().getRestaurantId());
            reservePayload.setAvailabilitySlotId(message.getPayload().getAvailabilitySlotId());
            reservePayload.setCustomerName(message.getPayload().getCustomerName());
            reservePayload.setCustomerEmail(message.getPayload().getCustomerEmail());
            reservePayload.setSeatsReserved(message.getPayload().getSeatsReserved());
            reservePayload.setStatus(message.getPayload().getStatus());
            reservePayload.setScheduledDay(message.getPayload().getScheduledDay());
            reservePayload.setScheduledTime(message.getPayload().getScheduledTime());

            producer.sendPaymentPaidEvent(reservePayload);

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
