package pt.ulusofona.cd.projeto.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@JsonPropertyOrder({ "id", "reservationId", "eventType", "recipient", "status", "sentAt" })
public class NotificationResponse {
    private UUID id;
    private UUID reservationId;
    private String eventType;
    private String recipient;
    private String status;
    private Instant sentAt;
}
