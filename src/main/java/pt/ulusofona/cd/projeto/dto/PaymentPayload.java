package pt.ulusofona.cd.projeto.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter

public class PaymentPayload {
    private UUID id;
    private UUID reservationId;
    private float amount;
    private String currency;
    private String status;
}
