package pt.ulusofona.cd.projeto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pt.ulusofona.cd.projeto.dto.RestaurantRequest;
import pt.ulusofona.cd.projeto.dto.RestaurantResponse;
import pt.ulusofona.cd.projeto.mapper.RestaurantMapper;
import pt.ulusofona.cd.projeto.model.Restaurant;
import pt.ulusofona.cd.projeto.repository.RestaurantRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    // Const
    private final RestaurantRepository repository;

    // Get
    public List<Restaurant> getAllRestaurants (){
        return repository.findAll();
    }

    public Restaurant getRestaurantById (Long restaurantId){
        return repository.findById(restaurantId).orElseThrow();
    }




    // Post
    public Restaurant createRestaurant(RestaurantRequest request){
        Restaurant restaurant = RestaurantMapper.toEntity(request);
        return repository.save(restaurant);
    }




    // Put
    public Restaurant updateRestaurant (Long restaurantId, RestaurantRequest request){
        Restaurant restaurant = repository.findById(restaurantId).orElseThrow();

        restaurant.setName(request.getName());
        restaurant.setCity(request.getCity());
        restaurant.setCountry(request.getCountry());
        restaurant.setPhone(request.getPhone());
        restaurant.setEmail(request.getEmail());

        return repository.save(restaurant);
    }




    // Delete
    public Restaurant deleteRestaurant (Long restaurantId){
        Restaurant restaurant = repository.findById(restaurantId).orElseThrow();
        repository.deleteById(restaurantId);
        return restaurant;
    }

}
