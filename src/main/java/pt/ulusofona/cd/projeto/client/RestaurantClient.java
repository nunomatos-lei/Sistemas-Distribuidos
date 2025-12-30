package pt.ulusofona.cd.projeto.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pt.ulusofona.cd.projeto.dto.MenuItemDto;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "restaurant-service", url = "http://restaurant-service:8081")
public interface RestaurantClient {

    @GetMapping("api/v1/restaurants/{restaurantId}/MenuItems")
    List<MenuItemDto> getMenuItemByRestaurantId(@PathVariable UUID restaurantId);

}
