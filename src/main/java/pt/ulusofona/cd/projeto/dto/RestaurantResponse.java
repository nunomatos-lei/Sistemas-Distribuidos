package pt.ulusofona.cd.projeto.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({ "id", "name", "city", "country", "phone", "email" })
public class RestaurantResponse {
    private Long id;
    private String name;
    private String city;
    private String country;
    private String phone;
    private String email;
}
