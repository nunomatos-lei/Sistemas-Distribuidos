package pt.ulusofona.cd.projeto.events;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pt.ulusofona.cd.projeto.dto.MessageEnvelope;
import pt.ulusofona.cd.projeto.dto.ReservationPayload;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${payment.events.payment-paid-events}")
    private String paymentPaidEventsTopic;

    public void sendPaymentPaidEvent(ReservationPayload payload) {
        MessageEnvelope<ReservationPayload> envelope = new MessageEnvelope<>(
                UUID.randomUUID(),                // messageId
                "PaymentPaid",             // type
                Instant.now(),                    // timestamp
                payload.getId(),                  // correlationId (use reservation ID for traceability)
                "reservation-service:Paid",     // causationId (originating operation)
                payload                           // payload (the actual reservation data)
        );

        kafkaTemplate.send(paymentPaidEventsTopic, payload.getId().toString(), envelope)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("Published ReservationPaid event for reservation: " + payload.getId());
                    } else {
                        System.err.println("Failed to publish ReservationPaid event: " + ex.getMessage());
                    }
                });
    }
}
