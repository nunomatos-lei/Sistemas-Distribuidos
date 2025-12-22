package pt.ulusofona.cd.projeto.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class NotificationRequest {
    @NotNull(message = "Reservation ID is required")
    private UUID reservationId;

    @NotBlank(message = "Event type is required")
    @Size(max = 50)
    private String eventType;

    @NotBlank(message = "Recipient is required")
    @Size(max = 255)
    private String recipient;

    @NotBlank(message = "Status is required")
    @Size(max = 20)
    private String status;
}
