package pt.ulusofona.cd.projeto.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class AvailabilitySlotController {

    // Const
    private final AvailabilitySlotService service;

    // Get
    @Operation(
            summary = "Get all availability slots",
            description = "Returns a complete list of all availability slots recorded in the system."
    )
    @GetMapping("/availabilitySlots")
    public ResponseEntity<List<AvailabilitySlotResponse>> getAllAvailabilitySlots(){
        List<AvailabilitySlotResponse> responses = service.getAllAvailabilitySlots().stream().map(AvailabilitySlotMapper::toResponse).toList();
        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "Get slot by ID",
            description = "Returns a specific availability slot based on its UUID."
    )
    @GetMapping("/availabilitySlots/{availabilitySlotId}")
    public ResponseEntity<AvailabilitySlotResponse> getAvailabilitySlotsById(@PathVariable UUID availabilitySlotId){
        AvailabilitySlot availabilitySlot = service.getAvailabilitySlotsById(availabilitySlotId);
        AvailabilitySlotResponse response = AvailabilitySlotMapper.toResponse(availabilitySlot);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get slots by restaurant",
            description = "Returns availability slots for a specific restaurant. Can be optionally filtered by date and time."
    )
    @GetMapping("/{restaurantId}/availabilitySlots")
    public ResponseEntity<List<AvailabilitySlotResponse>> getAvailabilitySlotsByRestaurantId(@PathVariable UUID restaurantId, @RequestParam(required = false) LocalDate date, @RequestParam(required = false) LocalTime time){
        List<AvailabilitySlotResponse> responses = service.getAvailabilitySlotsByRestaurantId(restaurantId, date, time).stream().map(AvailabilitySlotMapper::toResponse).toList();
        return ResponseEntity.ok(responses);
    }




    // Post
    @Operation(
            summary = "Create new slot",
            description = "Creates a new availability slot definition for a restaurant."
    )
    @PostMapping("/availabilitySlots")
    public ResponseEntity<AvailabilitySlotResponse> createAvailabilitySlot(@Valid @RequestBody AvailabilitySlotRequest request){
        AvailabilitySlot availabilitySlot =  service.createAvailabilitySlot(request);
        AvailabilitySlotResponse response = AvailabilitySlotMapper.toResponse(availabilitySlot);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Update seat capacity",
            description = "Increases or decreases the number of available seats for a specific slot."
    )
    @PostMapping("/availabilitySlots/{availabilitySlotId}/updateSeats")
    public ResponseEntity<AvailabilitySlotResponse> updateSeats(@PathVariable UUID availabilitySlotId, @RequestParam(required = true) int seatUpdate){
        AvailabilitySlot availabilitySlot =  service.updateSeats(availabilitySlotId, seatUpdate);
        AvailabilitySlotResponse response = AvailabilitySlotMapper.toResponse(availabilitySlot);
        return ResponseEntity.ok(response);
    }




    // Put
    @Operation(
            summary = "Update slot details",
            description = "Updates the general information of an existing availability slot."
    )
    @PutMapping("/availabilitySlots/{availabilitySlotId}")
    public ResponseEntity<AvailabilitySlotResponse> updateAvailabilitySlot(@PathVariable UUID availabilitySlotId, @Valid @RequestBody AvailabilitySlotRequest request){
        AvailabilitySlot availabilitySlot = service.updateAvailabilitySlot(availabilitySlotId, request);
        AvailabilitySlotResponse response = AvailabilitySlotMapper.toResponse(availabilitySlot);
        return ResponseEntity.ok(response);
    }




    // Delete
    @Operation(
            summary = "Delete slot",
            description = "Permanently removes an availability slot from the system."
    )
    @DeleteMapping("/availabilitySlots/{availabilitySlotId}")
    public ResponseEntity<AvailabilitySlotResponse> deleteAvailabilitySlot(@PathVariable UUID availabilitySlotId){
        AvailabilitySlot availabilitySlot = service.deleteAvailabilitySlot(availabilitySlotId);
        AvailabilitySlotResponse response = AvailabilitySlotMapper.toResponse(availabilitySlot);
        return ResponseEntity.ok(response);
    }
}
