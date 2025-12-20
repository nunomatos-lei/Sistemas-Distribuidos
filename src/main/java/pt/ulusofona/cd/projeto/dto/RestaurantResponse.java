package pt.ulusofona.cd.projeto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantResponse {
    private String name;
    private String city;
    private String country;
    private String phone;
    private String email;
}
