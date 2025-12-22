package pt.ulusofona.cd.projeto.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReservationResponse {
    private UUID reservationID;
    private UUID restaurantID;
    private String customerName;
    private String customerEmail;
    private int partySize;
    private String status;
}
