package pt.ulusofona.cd.projeto.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonPropertyOrder({ "id", "reservationId", "restaurantId", "customerName", "customerEmail", "taxNumber", "price", "currency"})
public class PaymentResponse {
    private UUID id;
    private UUID reservationId;
    private UUID restaurantId;
    private String customerName;
    private String customerEmail;
    private String taxNumber;
    private float price;
    private String currency;
}
