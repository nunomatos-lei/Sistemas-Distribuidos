package pt.ulusofona.cd.projeto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationUpdateRequest {
    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank
    @Email
    @Size(max = 100)
    private String customerEmail;
}
