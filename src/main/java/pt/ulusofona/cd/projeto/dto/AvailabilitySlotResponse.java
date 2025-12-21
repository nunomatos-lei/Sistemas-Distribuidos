package pt.ulusofona.cd.projeto.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@JsonPropertyOrder({ "id", "restaurant_id", "date", "start_time", "end_time", "capacity", "seats_available"})
public class AvailabilitySlotResponse {
    private Long id;
    private Long restaurant_id;
    private LocalDate date;
    private LocalTime start_time;
    private LocalTime end_time;
    private int capacity;
    private int seats_available;
}
