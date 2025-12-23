package pt.ulusofona.cd.projeto.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pt.ulusofona.cd.projeto.dto.AvailabilitySlotRequest;
import pt.ulusofona.cd.projeto.dto.AvailabilitySlotResponse;
import pt.ulusofona.cd.projeto.mapper.AvailabilitySlotMapper;
import pt.ulusofona.cd.projeto.model.AvailabilitySlot;
import pt.ulusofona.cd.projeto.service.AvailabilitySlotService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class AvailabilitySlotController {

    // Const
    private final AvailabilitySlotService service;

    // Get
    @GetMapping("/availabilitySlots")
    public ResponseEntity<List<AvailabilitySlotResponse>> getAllAvailabilitySlots(){
        List<AvailabilitySlotResponse> responses = service.getAllAvailabilitySlots().stream().map(AvailabilitySlotMapper::toResponse).toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/availabilitySlots/{availabilitySlotId}")
    public ResponseEntity<AvailabilitySlotResponse> getAvailabilitySlotsById(@PathVariable Long availabilitySlotId){
        AvailabilitySlot availabilitySlot = service.getAvailabilitySlotsById(availabilitySlotId);
        AvailabilitySlotResponse response = AvailabilitySlotMapper.toResponse(availabilitySlot);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{restaurantId}/availabilitySlots")
    public ResponseEntity<List<AvailabilitySlotResponse>> getAvailabilitySlotsByRestaurantId(@PathVariable Long restaurantId, @RequestParam(required = false) LocalDate date, @RequestParam(required = false) LocalTime time){
        List<AvailabilitySlotResponse> responses = service.getAvailabilitySlotsByRestaurantId(restaurantId, date, time).stream().map(AvailabilitySlotMapper::toResponse).toList();
        return ResponseEntity.ok(responses);
    }




    // Post
    @PostMapping("/availabilitySlots")
    public ResponseEntity<AvailabilitySlotResponse> createAvailabilitySlot(@Valid @RequestBody AvailabilitySlotRequest request){
        AvailabilitySlot availabilitySlot =  service.createAvailabilitySlot(request);
        AvailabilitySlotResponse response = AvailabilitySlotMapper.toResponse(availabilitySlot);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }




    // Put
    @PutMapping("/availabilitySlots/{availabilitySlotId}")
    public ResponseEntity<AvailabilitySlotResponse> updateAvailabilitySlot(@PathVariable Long availabilitySlotId, @Valid @RequestBody AvailabilitySlotRequest request){
        AvailabilitySlot availabilitySlot = service.updateAvailabilitySlot(availabilitySlotId, request);
        AvailabilitySlotResponse response = AvailabilitySlotMapper.toResponse(availabilitySlot);
        return ResponseEntity.ok(response);
    }




    // Delete
    @DeleteMapping("/availabilitySlots/{availabilitySlotId}")
    public ResponseEntity<AvailabilitySlotResponse> deleteAvailabilitySlot(@PathVariable Long availabilitySlotId){
        AvailabilitySlot availabilitySlot = service.deleteAvailabilitySlot(availabilitySlotId);
        AvailabilitySlotResponse response = AvailabilitySlotMapper.toResponse(availabilitySlot);
        return ResponseEntity.ok(response);
    }
}
