package pt.ulusofona.cd.projeto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItemRequest {
    @NotNull(message = "Menu item restaurant id is required")
    private Long restaurant_id;

    @NotBlank(message = "Menu item name is required")
    private String name;

    @NotBlank(message = "Menu item description is required")
    private String description;

    @NotNull(message = "Menu item price is required")
    private float price;

    @NotBlank(message = "Menu item currency is required")
    @Size(min = 2, max = 7, message = "Insert a valid currency")
    private String currency;
}
