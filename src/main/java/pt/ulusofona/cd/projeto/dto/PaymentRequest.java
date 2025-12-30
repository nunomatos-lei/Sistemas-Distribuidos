package pt.ulusofona.cd.projeto.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PaymentRequest {

    @NotNull
    private UUID reservationId;

    @NotNull
    private UUID restaurantId;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Email is required")
    @Email
    @Size(max = 100)
    private String customerEmail;

    @NotBlank
    @Size(max = 20)
    private String taxNumber;

    private float price = 0.0F;

    private String currency;
}
