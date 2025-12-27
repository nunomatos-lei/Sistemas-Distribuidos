package pt.ulusofona.cd.projeto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pt.ulusofona.cd.projeto.dto.MenuItemRequest;
import pt.ulusofona.cd.projeto.exception.InvalidMenuItemException;
import pt.ulusofona.cd.projeto.exception.MenuItemNotFoundException;
import pt.ulusofona.cd.projeto.mapper.MenuItemMapper;
import pt.ulusofona.cd.projeto.model.MenuItem;
import pt.ulusofona.cd.projeto.repository.MenuItemRepository;
import pt.ulusofona.cd.projeto.repository.RestaurantRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    // Const
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;




    // Func
    public void menuItemCheck(MenuItem menuItem){
        if (menuItem.getPrice() < 0){
            throw new InvalidMenuItemException("Price cannot be negative");
        }
    }




    // Get
    public List<MenuItem> getAllMenuItems(){
        return menuItemRepository.findAll();
    }

    public MenuItem getMenuItemById(UUID menuItemId){
        return menuItemRepository.findById(menuItemId).orElseThrow(() -> new MenuItemNotFoundException("Menu item with id " + menuItemId + " not found"));
    }

    public List<MenuItem> getMenuItemsByRestaurantId(@PathVariable UUID restaurantId){
        return menuItemRepository.findAllByRestaurantId(restaurantId);
    }




    // Post
    @Transactional
    public MenuItem createMenuItem(MenuItemRequest request){
        restaurantRepository.findById(request.getRestaurantId()).orElseThrow(() -> new MenuItemNotFoundException("Restaurant with id " + request.getRestaurantId() + " not found"));
        MenuItem menuItem = MenuItemMapper.toEntity(request);
        menuItemCheck(menuItem);
        return menuItemRepository.save(menuItem);
    }




    // Put
    @Transactional
    public MenuItem updateMenuItem(UUID menuItemId, MenuItemRequest request){
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> new MenuItemNotFoundException("Menu item with id " + menuItemId + " not found"));

        menuItem.setRestaurantId(request.getRestaurantId());
        menuItem.setName(request.getName());
        menuItem.setDescription(request.getDescription());
        menuItem.setPrice(request.getPrice());
        menuItem.setCurrency(request.getCurrency());
        menuItemCheck(menuItem);

        return menuItemRepository.save(menuItem);
    }




    // Delete
    @Transactional
    public MenuItem deleteMenuItem(UUID menuItemId){
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> new MenuItemNotFoundException("Menu item with id " + menuItemId + " not found"));
        menuItemRepository.deleteById(menuItemId);
        return menuItem;
    }
}
