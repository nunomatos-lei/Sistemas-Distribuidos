package pt.ulusofona.cd.projeto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class AvailabilitySlotRequest {
    @NotNull(message = "Availability slot restaurant id is required")
    private UUID restaurantId;

    @NotNull(message = "Availability slot date is required")
    private LocalDate date;

    @NotNull(message = "Availability slot start time is required")
    private LocalTime startTime;

    @NotNull(message = "Availability slot end time is required")
    private LocalTime endTime;

    @NotNull(message = "Availability slot capacity is required")
    private int capacity;

    @NotNull(message = "Availability slot seats available is required")
    private int seatsAvailable;
}
