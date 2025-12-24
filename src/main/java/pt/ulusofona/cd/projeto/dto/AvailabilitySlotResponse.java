package pt.ulusofona.cd.projeto.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@JsonPropertyOrder({ "id", "restaurantId", "date", "startTime", "endTime", "capacity", "seatsAvailable"})
public class AvailabilitySlotResponse {
    private UUID id;
    private UUID restaurantId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int capacity;
    private int seatsAvailable;
}
