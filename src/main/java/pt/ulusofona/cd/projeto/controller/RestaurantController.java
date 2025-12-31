package pt.ulusofona.cd.projeto.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ulusofona.cd.projeto.dto.MenuItemResponse;
import pt.ulusofona.cd.projeto.dto.RestaurantRequest;
import pt.ulusofona.cd.projeto.dto.RestaurantResponse;
import pt.ulusofona.cd.projeto.mapper.RestaurantMapper;
import pt.ulusofona.cd.projeto.model.Restaurant;
import pt.ulusofona.cd.projeto.service.RestaurantService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    // Const
    private final RestaurantService service;

    // Get
    @Operation(
            summary = "Get all restaurants",
            description = "Returns a complete list of all restaurants registered in the system."
    )
    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {
        List<RestaurantResponse> responses;
        responses = service.getAllRestaurants().stream().map(RestaurantMapper::toResponse).toList();
        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "Get restaurant by ID",
            description = "Returns a specific restaurant based on its unique UUID."
    )
    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable UUID restaurantId) {
        Restaurant restaurant = service.getRestaurantById(restaurantId);
        RestaurantResponse response = RestaurantMapper.toResponse(restaurant);
        return ResponseEntity.ok(response);
    }




    // Post
    @Operation(
            summary = "Create new restaurant",
            description = "Creates a new restaurant profile in the database."
    )
    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(@Valid @RequestBody RestaurantRequest request){
        Restaurant restaurant = service.createRestaurant(request);
        RestaurantResponse response = RestaurantMapper.toResponse(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }




    // Put
    @Operation(
            summary = "Update restaurant",
            description = "Updates the details of an existing restaurant."
    )
    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> updateRestaurant(@PathVariable UUID restaurantId, @Valid @RequestBody RestaurantRequest request){
        Restaurant restaurant = service.updateRestaurant(restaurantId, request);
        RestaurantResponse response = RestaurantMapper.toResponse(restaurant);
        return ResponseEntity.ok(response);
    }




    // Delete
    @Operation(
            summary = "Delete restaurant",
            description = "Permanently removes a restaurant from the system."
    )
    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> deleteRestaurant(@PathVariable UUID restaurantId){
        Restaurant restaurant = service.deleteRestaurant(restaurantId);
        RestaurantResponse response = RestaurantMapper.toResponse(restaurant);
        return ResponseEntity.ok(response);
    }
}
