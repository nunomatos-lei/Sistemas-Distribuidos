package pt.ulusofona.cd.projeto.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pt.ulusofona.cd.projeto.dto.MenuItemRequest;
import pt.ulusofona.cd.projeto.dto.MenuItemResponse;
import pt.ulusofona.cd.projeto.mapper.MenuItemMapper;
import pt.ulusofona.cd.projeto.model.MenuItem;
import pt.ulusofona.cd.projeto.service.MenuItemService;

import java.awt.*;
import java.util.List;

@Controller
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class MenuItemController {

    // Const
    private final MenuItemService service;

    // Get
    @GetMapping("/MenuItems")
    public ResponseEntity<List<MenuItemResponse>> getAllMenuItems(){
        List<MenuItemResponse> responses = service.getAllMenuItems().stream().map(MenuItemMapper::toResponse).toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/MenuItems/{menuItemId}")
    public ResponseEntity<MenuItemResponse> getMenuItemById(@PathVariable Long menuItemId){
        MenuItem menuItem = service.getMenuItemById(menuItemId);
        MenuItemResponse response = MenuItemMapper.toResponse(menuItem);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{restaurantId}/MenuItems")
    public ResponseEntity<List<MenuItemResponse>> getMenuItemsByRestaurantId(@PathVariable Long restaurantId){
        List<MenuItemResponse> responses = service.getMenuItemsByRestaurantId(restaurantId).stream().map(MenuItemMapper::toResponse).toList();
        return ResponseEntity.ok(responses);
    }




    // Post
    @PostMapping("/MenuItems")
    public ResponseEntity<MenuItemResponse> createMenuItem(@Valid @RequestBody MenuItemRequest request){
        MenuItem item = service.createMenuItem(request);
        MenuItemResponse response = MenuItemMapper.toResponse(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }




    // Put
    @PutMapping("/MenuItems/{menuItemId}")
    public ResponseEntity<MenuItemResponse> updateMenuItem(@PathVariable Long menuItemId, @Valid @RequestBody MenuItemRequest request){
        MenuItem menuItem = service.updateMenuItem(menuItemId, request);
        MenuItemResponse response = MenuItemMapper.toResponse(menuItem);
        return ResponseEntity.ok(response);
    }




    // Delete
    @DeleteMapping("/MenuItems/{menuItemId}")
    public ResponseEntity<MenuItemResponse> deleteMenuItem(@PathVariable Long menuItemId){
        MenuItem menuItem = service.deleteMenuItem(menuItemId);
        MenuItemResponse response = MenuItemMapper.toResponse(menuItem);
        return ResponseEntity.ok(response);
    }
}
