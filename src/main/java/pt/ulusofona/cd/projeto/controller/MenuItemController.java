package pt.ulusofona.cd.projeto.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.ulusofona.cd.projeto.dto.MenuItemRequest;
import pt.ulusofona.cd.projeto.dto.MenuItemResponse;
import pt.ulusofona.cd.projeto.mapper.MenuItemMapper;
import pt.ulusofona.cd.projeto.model.MenuItem;
import pt.ulusofona.cd.projeto.service.MenuItemService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class MenuItemController {

    // Const
    private final MenuItemService service;

    // Get
    @GetMapping("/Menu_Items")
    public ResponseEntity<List<MenuItemResponse>> getAllMenuItems(){
        List<MenuItemResponse> responses = service.getAllMenuItems().stream().map(MenuItemMapper::toResponse).toList();
        return ResponseEntity.ok(responses);
    }




    // Post
    @PostMapping("/Menu_Items")
    public ResponseEntity<MenuItemResponse> createMenuItem(@Valid @RequestBody MenuItemRequest request){
        MenuItem item = service.createMenuItem(request);
        MenuItemResponse response = MenuItemMapper.toResponse(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
