package pt.ulusofona.cd.projeto.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ulusofona.cd.projeto.dto.AvailabilitySlotDto;
import pt.ulusofona.cd.projeto.dto.MenuItemDto;
import pt.ulusofona.cd.projeto.dto.RestaurantDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@FeignClient(name = "restaurant-service", url = "http://restaurant-service:8081")
public interface RestaurantClient {

    // Get
    @GetMapping("/api/v1/restaurants/{restaurantId}/availabilitySlots")
    List<AvailabilitySlotDto> getAvailabilitySlotsByRestaurantId(@PathVariable UUID restaurantId, @RequestParam(required = false) LocalDate date, @RequestParam(required = false) LocalTime time);

    @GetMapping("/api/v1/restaurants/{restaurantId}")
    RestaurantDto getRestaurantById(@PathVariable UUID restaurantId);

    @GetMapping("/api/v1/restaurants/MenuItems/{menuItemId}")
    public ResponseEntity<MenuItemDto> getMenuItemById(@PathVariable UUID menuItemId);




    // Post
    @PostMapping("/api/v1/restaurants/availabilitySlots/{availabilitySlotId}/updateSeats")
    RestaurantDto updateSeats(@PathVariable UUID availabilitySlotId, @RequestParam(required = true) int seatUpdate);

}
