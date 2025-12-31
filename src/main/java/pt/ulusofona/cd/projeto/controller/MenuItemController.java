package pt.ulusofona.cd.projeto.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class MenuItemController {

    // Const
    private final MenuItemService service;

    // Get
    @Operation(
            summary = "Get all menu items",
            description = "Returns a global list of all menu items registered in the system (across all restaurants)."
    )
    @GetMapping("/MenuItems")
    public ResponseEntity<List<MenuItemResponse>> getAllMenuItems(){
        List<MenuItemResponse> responses = service.getAllMenuItems().stream().map(MenuItemMapper::toResponse).toList();
        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "Get menu item by ID",
            description = "Returns details of a specific menu item based on its UUID."
    )
    @GetMapping("/MenuItems/{menuItemId}")
    public ResponseEntity<MenuItemResponse> getMenuItemById(@PathVariable UUID menuItemId){
        MenuItem menuItem = service.getMenuItemById(menuItemId);
        MenuItemResponse response = MenuItemMapper.toResponse(menuItem);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get items by restaurant",
            description = "Returns the specific menu list associated with a given restaurant."
    )
    @GetMapping("/{restaurantId}/MenuItems")
    public ResponseEntity<List<MenuItemResponse>> getMenuItemsByRestaurantId(@PathVariable UUID restaurantId){
        List<MenuItemResponse> responses = service.getMenuItemsByRestaurantId(restaurantId).stream().map(MenuItemMapper::toResponse).toList();
        return ResponseEntity.ok(responses);
    }




    // Post
    @Operation(
            summary = "Create menu item",
            description = "Creates a new dish or drink entry for a restaurant."
    )
    @PostMapping("/MenuItems")
    public ResponseEntity<MenuItemResponse> createMenuItem(@Valid @RequestBody MenuItemRequest request){
        MenuItem item = service.createMenuItem(request);
        MenuItemResponse response = MenuItemMapper.toResponse(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }




    // Put
    @Operation(
            summary = "Update menu item",
            description = "Updates the details (name, price, description) of an existing menu item."
    )
    @PutMapping("/MenuItems/{menuItemId}")
    public ResponseEntity<MenuItemResponse> updateMenuItem(@PathVariable UUID menuItemId, @Valid @RequestBody MenuItemRequest request){
        MenuItem menuItem = service.updateMenuItem(menuItemId, request);
        MenuItemResponse response = MenuItemMapper.toResponse(menuItem);
        return ResponseEntity.ok(response);
    }




    // Delete
    @Operation(
            summary = "Delete menu item",
            description = "Permanently removes a menu item from the system."
    )
    @DeleteMapping("/MenuItems/{menuItemId}")
    public ResponseEntity<MenuItemResponse> deleteMenuItem(@PathVariable UUID menuItemId){
        MenuItem menuItem = service.deleteMenuItem(menuItemId);
        MenuItemResponse response = MenuItemMapper.toResponse(menuItem);
        return ResponseEntity.ok(response);
    }
}
