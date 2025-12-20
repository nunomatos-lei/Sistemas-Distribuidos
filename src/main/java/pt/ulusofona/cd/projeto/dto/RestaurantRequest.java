package pt.ulusofona.cd.projeto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantRequest {
    @NotBlank(message = "Restaurant name is required")
    private String name;

    @NotBlank(message = "Restaurant city is required")
    private String city;

    @NotBlank(message = "Restaurant country is required")
    private String country;

    @NotBlank(message = "Restaurant phone is required")
    @Size(min = 9, max = 16, message = "Insert a valid phone number")
    private String phone;

    @NotBlank(message = "Restaurant email is required")
    private String email;
}
