package pt.ulusofona.cd.projeto.mapper;

import pt.ulusofona.cd.projeto.dto.MenuItemRequest;
import pt.ulusofona.cd.projeto.dto.MenuItemResponse;
import pt.ulusofona.cd.projeto.model.MenuItem;

public class MenuItemMapper {

    public static MenuItem toEntity(MenuItemRequest request){
        MenuItem menuItem = new MenuItem();
        menuItem.setRestaurant_id(request.getRestaurant_id());
        menuItem.setName(request.getName());
        menuItem.setDescription(request.getDescription());
        menuItem.setPrice(request.getPrice());
        menuItem.setCurrency(request.getCurrency());
        return menuItem;
    }

    public static MenuItemResponse toResponse(MenuItem menuItem){
        MenuItemResponse response = new MenuItemResponse();
        response.setId(menuItem.getId());
        response.setRestaurant_id(menuItem.getRestaurant_id());
        response.setName(menuItem.getName());
        response.setDescription(menuItem.getDescription());
        response.setPrice(menuItem.getPrice());
        response.setCurrency(menuItem.getCurrency());
        return response;
    }

}
