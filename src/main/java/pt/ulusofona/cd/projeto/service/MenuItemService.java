package pt.ulusofona.cd.projeto.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import pt.ulusofona.cd.projeto.dto.MenuItemRequest;
import pt.ulusofona.cd.projeto.dto.MenuItemResponse;
import pt.ulusofona.cd.projeto.exception.InvalidAvailabilitySlotException;
import pt.ulusofona.cd.projeto.exception.InvalidMenuItemException;
import pt.ulusofona.cd.projeto.exception.MenuItemNotFoundException;
import pt.ulusofona.cd.projeto.exception.RestaurantNotFoundException;
import pt.ulusofona.cd.projeto.mapper.MenuItemMapper;
import pt.ulusofona.cd.projeto.model.AvailabilitySlot;
import pt.ulusofona.cd.projeto.model.MenuItem;
import pt.ulusofona.cd.projeto.repository.MenuItemRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    // Const
    private final MenuItemRepository repository;




    // Func
    public void menuItemCheck(MenuItem menuItem){
        if (menuItem.getPrice() < 0){
            throw new InvalidMenuItemException("Price cannot be negative");
        }
    }




    // Get
    public List<MenuItem> getAllMenuItems(){
        return repository.findAll();
    }

    public MenuItem getMenuItemById(UUID menuItemId){
        return repository.findById(menuItemId).orElseThrow(() -> new MenuItemNotFoundException("Menu item with id " + menuItemId + " not found"));
    }

    public List<MenuItem> getMenuItemsByRestaurantId(@PathVariable UUID restaurantId){
        return repository.findAllByRestaurantId(restaurantId);
    }




    // Post
    public MenuItem createMenuItem(MenuItemRequest request){
        MenuItem menuItem = MenuItemMapper.toEntity(request);
        menuItemCheck(menuItem);
        return repository.save(menuItem);
    }




    // Put
    public MenuItem updateMenuItem(UUID menuItemId, MenuItemRequest request){
        MenuItem menuItem = repository.findById(menuItemId).orElseThrow(() -> new MenuItemNotFoundException("Menu item with id " + menuItemId + " not found"));

        menuItem.setRestaurantId(request.getRestaurantId());
        menuItem.setName(request.getName());
        menuItem.setDescription(request.getDescription());
        menuItem.setPrice(request.getPrice());
        menuItem.setCurrency(request.getCurrency());
        menuItemCheck(menuItem);

        return repository.save(menuItem);
    }




    // Delete
    public MenuItem deleteMenuItem(UUID menuItemId){
        MenuItem menuItem = repository.findById(menuItemId).orElseThrow(() -> new MenuItemNotFoundException("Menu item with id " + menuItemId + " not found"));
        repository.deleteById(menuItemId);
        return menuItem;
    }
}
