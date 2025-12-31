package pt.ulusofona.cd.projeto.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@JsonPropertyOrder({ "id", "restaurantId", "availabilitySlotId", "customerName", "customerEmail", "seatsReserved", "status", "amount", "currency", "scheduledDay", "scheduledTime" })
public class ReservationResponse {
    private UUID id;
    private UUID restaurantId;
    private UUID availabilitySlotId;
    private String customerName;
    private String customerEmail;
    private int seatsReserved;
    private String status;
    private float amount;
    private String currency;
    private LocalDate scheduledDay;
    private LocalTime scheduledTime;
}
