package pt.ulusofona.cd.projeto.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import pt.ulusofona.cd.projeto.dto.AvailabilitySlotRequest;
import pt.ulusofona.cd.projeto.dto.AvailabilitySlotResponse;
import pt.ulusofona.cd.projeto.exception.AvailabilitySlotException;
import pt.ulusofona.cd.projeto.exception.MenuItemNotFoundException;
import pt.ulusofona.cd.projeto.mapper.AvailabilitySlotMapper;
import pt.ulusofona.cd.projeto.model.AvailabilitySlot;
import pt.ulusofona.cd.projeto.repository.AvailabilitySlotRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilitySlotService {

    // Const
    private final AvailabilitySlotRepository repository;

    // Get
    public List<AvailabilitySlot> getAllAvailabilitySlots(){
        return repository.findAll();
    }

    public AvailabilitySlot getAvailabilitySlotsById(Long availabilitySlotId){
        return repository.findById(availabilitySlotId).orElseThrow(() -> new AvailabilitySlotException("Availability slot with id " + availabilitySlotId + " not found"));
    }




    // Post
    public AvailabilitySlot createAvailabilitySlot(AvailabilitySlotRequest request){
        AvailabilitySlot availabilitySlot = AvailabilitySlotMapper.toEntity(request);
        return repository.save(availabilitySlot);
    }




    // Put
    public AvailabilitySlot updateAvailabilitySlot(Long availabilitySlotId, AvailabilitySlotRequest request){
        AvailabilitySlot availabilitySlot = repository.findById(availabilitySlotId).orElseThrow(() -> new AvailabilitySlotException("Availability slot with id " + availabilitySlotId + " not found"));

        availabilitySlot.setRestaurant_id(request.getRestaurant_id());
        availabilitySlot.setDate(request.getDate());
        availabilitySlot.setStart_time(request.getStart_time());
        availabilitySlot.setEnd_time(request.getEnd_time());
        availabilitySlot.setCapacity(request.getCapacity());
        availabilitySlot.setSeats_available(request.getSeats_available());

        return repository.save(availabilitySlot);
    }




    // Delete
    public AvailabilitySlot deleteAvailabilitySlot(Long availabilitySlotId){
        AvailabilitySlot availabilitySlot = repository.findById(availabilitySlotId).orElseThrow(() -> new AvailabilitySlotException("Availability slot with id " + availabilitySlotId + " not found"));
        repository.deleteById(availabilitySlotId);
        return availabilitySlot;
    }
}
