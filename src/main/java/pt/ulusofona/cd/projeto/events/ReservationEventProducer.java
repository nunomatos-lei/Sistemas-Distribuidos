package pt.ulusofona.cd.projeto.events;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pt.ulusofona.cd.projeto.dto.MessageEnvelope;
import pt.ulusofona.cd.projeto.dto.ReservationResponse;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${reservation.events.reservation-created-events}")
    private String reservationCreatedEventsTopic;

    @Value("${reservation.events.reservation-confirmed-events}")
    private String reservationConfirmedEventsTopic;

    @Value("${reservation.events.reservation-canceled-events}")
    private String reservationCanceledEventsTopic;

    @Value("${reservation.events.reservation-payment-events}")
    private String reservationPaymentEventsTopic;

    public void sendReservationCreatedEvent(ReservationResponse payload) {
        MessageEnvelope<ReservationResponse> envelope = new MessageEnvelope<>(
                UUID.randomUUID(),                // messageId
                "ReservationCreated",             // type
                Instant.now(),                    // timestamp
                payload.getId(),                  // correlationId (use reservation ID for traceability)
                "reservation-service:create",     // causationId (originating operation)
                payload                           // payload (the actual reservation data)
        );

        kafkaTemplate.send(reservationCreatedEventsTopic, payload.getId().toString(), envelope)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("Published ReservationCreated event for reservation: " + payload.getId());
                    } else {
                        System.err.println("Failed to publish ReservationCreated event: " + ex.getMessage());
                    }
                });
    }


    public void sendReservationConfirmedEvent(ReservationResponse payload) {
        MessageEnvelope<ReservationResponse> envelope = new MessageEnvelope<>(
                UUID.randomUUID(),                // messageId
                "ReservationConfirmed",             // type
                Instant.now(),                    // timestamp
                payload.getId(),                  // correlationId (use reservation ID for traceability)
                "reservation-service:confirmed",     // causationId (originating operation)
                payload                           // payload (the actual reservation data)
        );

        kafkaTemplate.send(reservationConfirmedEventsTopic, payload.getId().toString(), envelope)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("Published ReservationConfirmed event for reservation: " + payload.getId());
                    } else {
                        System.err.println("Failed to publish ReservationConfirmed event: " + ex.getMessage());
                    }
                });
    }


    public void sendReservationCanceledEvent(ReservationResponse payload) {
        MessageEnvelope<ReservationResponse> envelope = new MessageEnvelope<>(
                UUID.randomUUID(),                // messageId
                "ReservationCanceled",             // type
                Instant.now(),                    // timestamp
                payload.getId(),                  // correlationId (use reservation ID for traceability)
                "reservation-service:canceled",     // causationId (originating operation)
                payload                           // payload (the actual reservation data)
        );

        kafkaTemplate.send(reservationCanceledEventsTopic, payload.getId().toString(), envelope)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("Published ReservationCanceled event for reservation: " + payload.getId());
                    } else {
                        System.err.println("Failed to publish ReservationCanceled event: " + ex.getMessage());
                    }
                });
    }

    public void sendReservationPaymentEvent(ReservationResponse payload) {
        MessageEnvelope<ReservationResponse> envelope = new MessageEnvelope<>(
                UUID.randomUUID(),                // messageId
                "ReservationPayment",             // type
                Instant.now(),                    // timestamp
                payload.getId(),                  // correlationId (use reservation ID for traceability)
                "reservation-service:payment",     // causationId (originating operation)
                payload                           // payload (the actual reservation data)
        );

        kafkaTemplate.send(reservationPaymentEventsTopic, payload.getId().toString(), envelope)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("Published ReservationConfirmed event for reservation: " + payload.getId());
                    } else {
                        System.err.println("Failed to publish ReservationConfirmed event: " + ex.getMessage());
                    }
                });
    }
}
