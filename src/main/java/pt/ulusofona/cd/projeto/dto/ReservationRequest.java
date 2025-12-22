package pt.ulusofona.cd.projeto.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReservationRequest {

    @NotNull
    @NotBlank(message = "Restaurant ID is required")
    private UUID restaurantID;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank
    @Email
    @Size(max = 100)
    private String customerEmail;

    @Min(value = 0, message = "partySize cannot be negative")
    private int partySize;

    private String status;
}
