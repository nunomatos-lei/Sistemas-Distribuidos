package pt.ulusofona.cd.projeto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
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
