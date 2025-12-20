package pt.ulusofona.cd.projeto.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getRestaurants() {
        List<RestaurantResponse> responses;
        responses = service.getAll().stream().map(RestaurantMapper::toResponse).toList();
        return ResponseEntity.ok(responses);
    }




    // Post
    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(@Valid @RequestBody RestaurantRequest request){
        Restaurant restaurant = service.createRestaurant(request);
        RestaurantResponse response = RestaurantMapper.toResponse(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
