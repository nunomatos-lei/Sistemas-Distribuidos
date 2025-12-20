package pt.ulusofona.cd.projeto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
    public List<Restaurant> getAll (){
        return repository.findAll();
    }




    // Post
    public Restaurant createRestaurant(RestaurantRequest request){
        Restaurant restaurant = RestaurantMapper.toEntity(request);
        return repository.save(restaurant);
    }

}
