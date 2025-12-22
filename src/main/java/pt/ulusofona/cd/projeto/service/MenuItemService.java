package pt.ulusofona.cd.projeto.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import pt.ulusofona.cd.projeto.dto.MenuItemRequest;
import pt.ulusofona.cd.projeto.dto.MenuItemResponse;
import pt.ulusofona.cd.projeto.exception.MenuItemNotFoundException;
import pt.ulusofona.cd.projeto.exception.RestaurantNotFoundException;
import pt.ulusofona.cd.projeto.mapper.MenuItemMapper;
import pt.ulusofona.cd.projeto.model.MenuItem;
import pt.ulusofona.cd.projeto.repository.MenuItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    // Const
    private final MenuItemRepository repository;

    // Get
    public List<MenuItem> getAllMenuItems(){
        return repository.findAll();
    }

    public MenuItem getMenuItemById(Long menuItemId){
        return repository.findById(menuItemId).orElseThrow(() -> new MenuItemNotFoundException("Menu item with id " + menuItemId + " not found"));
    }




    // Post
    public MenuItem createMenuItem(MenuItemRequest request){
        MenuItem item = MenuItemMapper.toEntity(request);
        return repository.save(item);
    }




    // Put
    public MenuItem updateMenuItem(Long menuItemId, MenuItemRequest request){
        MenuItem menuItem = repository.findById(menuItemId).orElseThrow(() -> new MenuItemNotFoundException("Menu item with id " + menuItemId + " not found"));

        menuItem.setRestaurant_id(request.getRestaurant_id());
        menuItem.setName(request.getName());
        menuItem.setDescription(request.getDescription());
        menuItem.setPrice(request.getPrice());
        menuItem.setCurrency(request.getCurrency());

        return repository.save(menuItem);
    }




    // Delete
    public MenuItem deleteMenuItem(Long menuItemId){
        MenuItem menuItem = repository.findById(menuItemId).orElseThrow(() -> new MenuItemNotFoundException("Menu item with id " + menuItemId + " not found"));
        repository.deleteById(menuItemId);
        return menuItem;
    }
}
