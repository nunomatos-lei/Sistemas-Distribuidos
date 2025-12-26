package pt.ulusofona.cd.projeto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulusofona.cd.projeto.client.RestaurantClient;
import pt.ulusofona.cd.projeto.dto.AvailabilitySlotDto;
import pt.ulusofona.cd.projeto.events.ReservationEventProducer;
import pt.ulusofona.cd.projeto.dto.ReservationRequest;
import pt.ulusofona.cd.projeto.exception.InvalidReservationException;
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
    private final ReservationEventProducer eventProducer;


    //***************  Post  ***************//
    @Transactional
    public Reservation createReservation(ReservationRequest request) {
        AvailabilitySlotDto availabilitySlotDto = restaurantClient.getAvailabilitySlotsByRestaurantId(request.getRestaurantId(), request.getScheduledDay(), request.getScheduledTime()).getFirst();

        if (availabilitySlotDto.getSeatsAvailable() < request.getSeatsReserved()){
            throw new InvalidReservationException("There are not enough seats.");
        }

        Reservation reservation = ReservationMapper.toEntity(request);
        reservation.setAvailabilitySlotId(availabilitySlotDto.getId());

        Reservation save = reservationRepository.save(reservation);
        eventProducer.sendReservationCreatedEvent(ReservationMapper.toResponse(save));

        return save;
    }

    @Transactional
    public Reservation confirmReservation(UUID reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + reservationId));

        if(reservation.getStatus().equals("CONFIRM")){
            throw new InvalidReservationException("The reservation is already confirmed");
        }
        else if ((reservation.getStatus().equals("CANCEL")))
        {
            throw new InvalidReservationException("The reservation is canceled");
        }

        reservation.setStatus("CONFIRM");
        Reservation save = reservationRepository.save(reservation);
        restaurantClient.updateSeats(save.getAvailabilitySlotId(), -save.getSeatsReserved());
        eventProducer.sendReservationConfirmedEvent(ReservationMapper.toResponse(save));

        return save;
    }

    @Transactional
    public Reservation cancelReservation(UUID reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + reservationId));

        if ((reservation.getStatus().equals("CANCEL")))
        {
            throw new InvalidReservationException("The reservation is already canceled");
        }

        reservation.setStatus("CANCEL");
        Reservation save = reservationRepository.save(reservation);
        restaurantClient.updateSeats(save.getAvailabilitySlotId(), save.getSeatsReserved());
        eventProducer.sendReservationCanceledEvent(ReservationMapper.toResponse(save));

        return save;
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
