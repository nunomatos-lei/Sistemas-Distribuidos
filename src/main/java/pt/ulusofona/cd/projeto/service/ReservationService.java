package pt.ulusofona.cd.projeto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulusofona.cd.projeto.dto.ReservationRequest;
import pt.ulusofona.cd.projeto.exception.ReservationNotFoundException;
import pt.ulusofona.cd.projeto.mapper.ReservationMapper;
import pt.ulusofona.cd.projeto.model.Reservation;
import pt.ulusofona.cd.projeto.repository.ReservationRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Transactional
    public Reservation createReservation(ReservationRequest request) {
        Reservation reservation = ReservationMapper.toEntity(request);
        return reservationRepository.save(reservation);
    }

    public Reservation getReservationById(UUID id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + id));
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public Reservation updateReservation(UUID id, ReservationRequest reservationDetails) {
        Reservation reservation = getReservationById(id);

        reservation.setRestaurantID(reservationDetails.getRestaurantID());
        reservation.setCustomerName(reservationDetails.getCustomerName());
        reservation.setCustomerEmail(reservationDetails.getCustomerEmail());
        reservation.setPartySize(reservationDetails.getPartySize());
        reservation.setStatus(reservationDetails.getStatus());

        return reservationRepository.save(reservation);
    }

    @Transactional
    public void deleteReservation(UUID id) {
        Reservation reservation = getReservationById(id);
        reservationRepository.delete(reservation);
    }
}
