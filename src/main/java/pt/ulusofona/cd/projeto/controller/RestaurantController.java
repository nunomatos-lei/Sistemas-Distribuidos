package pt.ulusofona.cd.projeto.controller;

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

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    // Const
    private final RestaurantService service;

    // Restaurant table
    // Get
    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {
        List<RestaurantResponse> responses;
        responses = service.getAllRestaurants().stream().map(RestaurantMapper::toResponse).toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable Long restaurantId) {
        Restaurant restaurant = service.getRestaurantById(restaurantId);
        RestaurantResponse response = RestaurantMapper.toResponse(restaurant);
        return ResponseEntity.ok(response);
    }




    // Post
    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(@Valid @RequestBody RestaurantRequest request){
        Restaurant restaurant = service.createRestaurant(request);
        RestaurantResponse response = RestaurantMapper.toResponse(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }




    // Put
    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> updateRestaurant(@PathVariable Long restaurantId, @Valid @RequestBody RestaurantRequest request){
        Restaurant restaurant = service.updateRestaurant(restaurantId, request);
        RestaurantResponse response = RestaurantMapper.toResponse(restaurant);
        return ResponseEntity.ok(response);
    }




    // Delete
    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> deleteRestaurant(@PathVariable Long restaurantId){
        Restaurant restaurant = service.deleteRestaurant(restaurantId);
        RestaurantResponse response = RestaurantMapper.toResponse(restaurant);
        return ResponseEntity.ok(response);
    }
}
