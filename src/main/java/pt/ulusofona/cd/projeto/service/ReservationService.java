package pt.ulusofona.cd.projeto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulusofona.cd.projeto.client.RestaurantClient;
import pt.ulusofona.cd.projeto.dto.AvailabilitySlotDto;
import pt.ulusofona.cd.projeto.dto.ReservationRequest;
import pt.ulusofona.cd.projeto.dto.RestaurantDto;
import pt.ulusofona.cd.projeto.exception.ReservationNotFoundException;
import pt.ulusofona.cd.projeto.mapper.ReservationMapper;
import pt.ulusofona.cd.projeto.model.Reservation;
import pt.ulusofona.cd.projeto.repository.ReservationRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    // Const
    private final ReservationRepository reservationRepository;
    private final RestaurantClient restaurantClient;

    //***************  Post  ***************//
    @Transactional
    public Reservation createReservation(ReservationRequest request) {
        List<AvailabilitySlotDto> availabilitySlotDtos = restaurantClient.getAvailabilitySlotsByRestaurantId(request.getRestaurantId());
        Reservation reservation = ReservationMapper.toEntity(request);
        reservation.setAvailabilitySlotId(availabilitySlotDtos.getFirst().getId());
        return reservationRepository.save(reservation);
    }




    //***************  Get  ***************//
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(UUID id) {
        return reservationRepository.findById(id).orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + id));
    }





    //***************  Put  ***************//
    @Transactional
    public Reservation updateReservation(UUID id, ReservationRequest reservationDetails) {
        Reservation reservation = getReservationById(id);

        reservation.setRestaurantId(reservationDetails.getRestaurantId());
        reservation.setCustomerName(reservationDetails.getCustomerName());
        reservation.setCustomerEmail(reservationDetails.getCustomerEmail());
        reservation.setSeatsReserved(reservationDetails.getSeatsReserved());

        return reservationRepository.save(reservation);
    }




    //***************  Delete  ***************//
    @Transactional
    public void deleteReservation(UUID id) {
        Reservation reservation = getReservationById(id);
        reservationRepository.delete(reservation);
    }
}
