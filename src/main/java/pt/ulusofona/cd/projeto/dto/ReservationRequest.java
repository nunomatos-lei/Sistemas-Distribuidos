package pt.ulusofona.cd.projeto.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ReservationRequest {

    @NotNull
    private UUID restaurantId;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank
    @Email
    @Size(max = 100)
    private String customerEmail;

    @Min(value = 0, message = "partySize cannot be negative")
    private int seatsReserved;

    @NotNull
    private List<UUID> menuItemsId;

    @NotNull
    private LocalDate scheduledDay;

    @NotNull
    private LocalTime scheduledTime;
}
