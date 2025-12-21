package pt.ulusofona.cd.projeto.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({ "id", "restaurant_id", "name", "description", "price", "currency" })
public class MenuItemResponse {
    private Long id;
    private Long restaurant_id;
    private String name;
    private String description;
    private float price;
    private String currency;
}
