package pt.ulusofona.cd.projeto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.core.support.RepositoryMethodInvocationListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pt.ulusofona.cd.projeto.client.RestaurantClient;
import pt.ulusofona.cd.projeto.dto.AvailabilitySlotDto;
import pt.ulusofona.cd.projeto.dto.ReservationAddMenuItemsRequest;
import pt.ulusofona.cd.projeto.dto.ReservationUpdateRequest;
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
    private final RepositoryMethodInvocationListener repositoryMethodInvocationListener;


    //***************  Post  ***************//
    @Transactional
    public Reservation createReservation(ReservationRequest request) {
        List<AvailabilitySlotDto> availabilitySlotDtos = restaurantClient.getAvailabilitySlotsByRestaurantId(request.getRestaurantId(), request.getScheduledDay(), request.getScheduledTime());

        if(availabilitySlotDtos.isEmpty()){
            throw new InvalidReservationException("There is no availability");
        }

        AvailabilitySlotDto availabilitySlotDto = availabilitySlotDtos.getFirst();

        if (availabilitySlotDto.getSeatsAvailable() < request.getSeatsReserved()){
            throw new InvalidReservationException("There are not enough seats.");
        }

        Reservation reservation = ReservationMapper.toEntity(request);
        reservation.setAvailabilitySlotId(availabilitySlotDto.getId());

        float countAmount = 0;
        for (int i = 0; i < request.getMenuItemsId().toArray().length; i++){
            try{
                countAmount += restaurantClient.getMenuItemById(request.getMenuItemsId().get(i)).getBody().getPrice();
            }catch (RuntimeException e){
                throw  new InvalidReservationException("A menu item doesn't exist");
            }
        }
        reservation.setAmount(countAmount);
        reservation.setCurrency("EUR");

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
        try {
            restaurantClient.updateSeats(save.getAvailabilitySlotId(), -save.getSeatsReserved());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Not enough seats available");
        }
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

    public List<Reservation> getReservationsByRestaurantId(UUID restaurantId) {
        return reservationRepository.findByRestaurantId(restaurantId);
    }




    //***************  Put  ***************//
    @Transactional
    public Reservation addMenuItemsToReservation(UUID id, ReservationAddMenuItemsRequest request) {
        Reservation reservation = getReservationById(id);

        if (!reservation.getStatus().equals("PENDING")){
            throw new InvalidReservationException("The reservation is canceled or confirmed");
        }

        float countAmount = 0;
        for (int i = 0; i < request.getMenuItemsId().toArray().length; i++){
            try{
                countAmount += restaurantClient.getMenuItemById(request.getMenuItemsId().get(i)).getBody().getPrice();
            }catch (RuntimeException e){
                throw  new InvalidReservationException("A menu item doesn't exist");
            }
        }
        reservation.setAmount(reservation.getAmount() + countAmount);
        reservation.setCurrency("EUR");

        return reservationRepository.save(reservation);
    }




    //***************  Delete  ***************//
    @Transactional
    public Reservation deleteReservation(UUID id) {
        Reservation reservation = getReservationById(id);
        reservationRepository.deleteById(id);
        return reservation;
    }
}
