package pt.ulusofona.cd.projeto.controller;


import lombok.RequiredArgsConstructor;
import pt.ulusofona.cd.projeto.dto.ReservationRequest;
import pt.ulusofona.cd.projeto.dto.ReservationResponse;
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
    public ResponseEntity<ReservationResponse> create(
            @Valid @RequestBody ReservationRequest request
    ) {
        Reservation created = service.createReservation(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ReservationMapper.toResponse(created));
    }


    //***************  Get  ***************//


    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getById(@PathVariable UUID id) {
        Reservation reservation = service.getReservationById(id);
        return ResponseEntity.ok(ReservationMapper.toResponse(reservation));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAll() {
        List<Reservation> reservations = service.getAllReservations();
        List<ReservationResponse> responseList = reservations.stream()
                .map(ReservationMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responseList);
    }


    //***************  Put  ***************//


    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody ReservationRequest request
    ) {
        Reservation updated = service.updateReservation(id, request);
        return ResponseEntity.ok(ReservationMapper.toResponse(updated));
    }


    //***************  Delete  ***************//

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
