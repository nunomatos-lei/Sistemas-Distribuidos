package pt.ulusofona.cd.projeto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {
    private UUID id;
    private String name;
    private String city;
    private String country;
    private String phone;
    private String email;
}
