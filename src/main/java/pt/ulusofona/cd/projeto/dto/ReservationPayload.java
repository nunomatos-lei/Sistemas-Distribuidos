package pt.ulusofona.cd.projeto.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class ReservationPayload {
    private UUID id;
    private UUID restaurantId;
    private UUID availabilitySlotId;
    private String customerName;
    private String customerEmail;
    private int seatsReserved;
    private String status;
    private LocalDate scheduledDay;
    private LocalTime scheduledTime;
}
