package pt.ulusofona.cd.projeto.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonPropertyOrder({ "id", "restaurantId", "name", "description", "price", "currency" })
public class MenuItemResponse {
    private UUID id;
    private UUID restaurantId;
    private String name;
    private String description;
    private float price;
    private String currency;
}
