package pt.ulusofona.cd.projeto.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pt.ulusofona.cd.projeto.dto.MenuItemRequest;
import pt.ulusofona.cd.projeto.dto.MenuItemResponse;
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




    // Post
    public MenuItem createMenuItem(MenuItemRequest request){
        MenuItem item = MenuItemMapper.toEntity(request);
        return repository.save(item);
    }
}
