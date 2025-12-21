package pt.ulusofona.cd.projeto.mapper;

import pt.ulusofona.cd.projeto.dto.RestaurantRequest;
import pt.ulusofona.cd.projeto.dto.RestaurantResponse;
import pt.ulusofona.cd.projeto.model.Restaurant;

public class RestaurantMapper {

    public static Restaurant toEntity(RestaurantRequest request){
        Restaurant restaurant = new Restaurant();
        restaurant.setName(request.getName());
        restaurant.setCity(request.getCity());
        restaurant.setCountry(request.getCountry());
        restaurant.setPhone(request.getPhone());
        restaurant.setEmail(request.getEmail());
        return restaurant;
    }

    public static RestaurantResponse toResponse(Restaurant restaurant){
        RestaurantResponse response = new RestaurantResponse();
        response.setId(restaurant.getId());
        response.setName(restaurant.getName());
        response.setCity(restaurant.getCity());
        response.setCountry(restaurant.getCountry());
        response.setPhone(restaurant.getPhone());
        response.setEmail(restaurant.getEmail());
        return response;
    }

}
