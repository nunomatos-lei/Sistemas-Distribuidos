package pt.ulusofona.cd.projeto.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import pt.ulusofona.cd.projeto.dto.ReservationAddMenuItemsRequest;
import pt.ulusofona.cd.projeto.dto.ReservationRequest;
import pt.ulusofona.cd.projeto.dto.ReservationResponse;
import pt.ulusofona.cd.projeto.dto.ReservationUpdateRequest;
import pt.ulusofona.cd.projeto.model.Reservation;
import pt.ulusofona.cd.projeto.mapper.ReservationMapper;
import pt.ulusofona.cd.projeto.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;

    //***************  Post  ***************//
    @PostMapping
    @Operation(
            summary = "Create reservation",
            description = "Creates a new reservation request. The status usually starts as PENDING."
    )
    public ResponseEntity<ReservationResponse> create(@Valid @RequestBody ReservationRequest request) {
        Reservation created = service.createReservation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ReservationMapper.toResponse(created));
    }

    @Operation(
            summary = "Confirm reservation",
            description = "Manually updates the reservation status to CONFIRMED."
    )
    @PostMapping("/{reservationId}/confirm")
    public ResponseEntity<ReservationResponse> confirmReservation(@PathVariable UUID reservationId) {
        Reservation reservation = service.confirmReservation(reservationId);
        return ResponseEntity.ok(ReservationMapper.toResponse(reservation));
    }

    @Operation(
            summary = "Cancel reservation",
            description = "Updates the reservation status to CANCELLED."
    )
    @PostMapping("/{reservationId}/cancel")
    public ResponseEntity<ReservationResponse> cancelReservation(@PathVariable UUID reservationId) {
        Reservation reservation = service.cancelReservation(reservationId);
        return ResponseEntity.ok(ReservationMapper.toResponse(reservation));
    }





    //***************  Get  ***************//
    @Operation(
            summary = "Get all reservations",
            description = "Returns a complete list of all reservations in the system."
    )
    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAll() {
        List<Reservation> reservations = service.getAllReservations();
        List<ReservationResponse> responseList = reservations.stream().map(ReservationMapper::toResponse).toList();

        return ResponseEntity.ok(responseList);
    }

    @Operation(
            summary = "Get reservation by ID",
            description = "Returns the details of a specific reservation based on its UUID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getById(@PathVariable UUID id) {
        Reservation reservation = service.getReservationById(id);
        return ResponseEntity.ok(ReservationMapper.toResponse(reservation));
    }

    @Operation(
            summary = "Get reservations by restaurant",
            description = "Returns a list of all reservations associated with a specific restaurant ID."
    )
    @GetMapping("/{restaurantId}/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservationsByRestaurantId(@PathVariable UUID restaurantId) {
        List<Reservation> reservations = service.getReservationsByRestaurantId(restaurantId);
        List<ReservationResponse> responseList = reservations.stream().map(ReservationMapper::toResponse).toList();

        return ResponseEntity.ok(responseList);
    }






    //***************  Put  ***************//
    @PutMapping("/{id}")
    @Operation(
            summary = "Add menu items",
            description = "Adds specific menu items to an existing reservation and recalculates the total price."
    )
    public ResponseEntity<ReservationResponse> addMenuItemsToReservation(@PathVariable UUID id, @Valid @RequestBody ReservationAddMenuItemsRequest request) {
        Reservation updated = service.addMenuItemsToReservation(id, request);
        return ResponseEntity.ok(ReservationMapper.toResponse(updated));
    }




    //***************  Delete  ***************//
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete reservation",
            description = "Permanently removes a reservation record from the database."
    )
    public ResponseEntity<ReservationResponse> delete(@PathVariable UUID id) {
        Reservation reservation = service.deleteReservation(id);
        ReservationResponse response = ReservationMapper.toResponse(reservation);
        return ResponseEntity.ok(response);
    }
}
