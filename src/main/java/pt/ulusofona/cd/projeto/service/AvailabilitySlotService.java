package pt.ulusofona.cd.projeto.service;

import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.ulusofona.cd.projeto.dto.AvailabilitySlotRequest;
import pt.ulusofona.cd.projeto.exception.AvailabilitySlotNotFoundException;
import pt.ulusofona.cd.projeto.exception.InvalidAvailabilitySlotException;
import pt.ulusofona.cd.projeto.mapper.AvailabilitySlotMapper;
import pt.ulusofona.cd.projeto.model.AvailabilitySlot;
import pt.ulusofona.cd.projeto.repository.AvailabilitySlotRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilitySlotService {

    // Const
    private final AvailabilitySlotRepository repository;




    // Func
    public void availabilitySlotCheck(AvailabilitySlot availabilitySlot){
        if (availabilitySlot.getSeatsAvailable() < 0 || availabilitySlot.getCapacity() < 0){
            throw new InvalidAvailabilitySlotException("Seats available or capacity cannot be negative");
        }
        if (availabilitySlot.getSeatsAvailable() > availabilitySlot.getCapacity()){
            throw new InvalidAvailabilitySlotException("Seats available cannot be greater than capacity");
        }
    }




    // Get
    public List<AvailabilitySlot> getAllAvailabilitySlots(){
        return repository.findAll();
    }

    public AvailabilitySlot getAvailabilitySlotsById(Long availabilitySlotId){
        return repository.findById(availabilitySlotId).orElseThrow(() -> new AvailabilitySlotNotFoundException("Availability slot with id " + availabilitySlotId + " not found"));
    }

    public List<AvailabilitySlot> getAvailabilitySlotsByRestaurantId(Long restaurantId, LocalDate date, LocalTime time){

        List<AvailabilitySlot> availabilitySlots = repository.findAllByRestaurantId(restaurantId);
        // All params
        if(date != null && time != null){

            List<AvailabilitySlot> availabilitySlotsFilter = new ArrayList<>();
            for (AvailabilitySlot slot : availabilitySlots){

                if(slot.getDate().isEqual(date) && (slot.getStartTime().isBefore(time) && slot.getEndTime().isAfter(time))){
                    availabilitySlotsFilter.add(slot);
                }

            }
            return availabilitySlotsFilter;

        }
        // Date param
        else if (date != null){

            List<AvailabilitySlot> availabilitySlotsFilter = new ArrayList<>();
            for (AvailabilitySlot slot : availabilitySlots){

                if(slot.getDate().isEqual(date)){
                    availabilitySlotsFilter.add(slot);
                }

            }
            return availabilitySlotsFilter;

        }
        // Time param
        else if (time != null){

            List<AvailabilitySlot> availabilitySlotsFilter = new ArrayList<>();
            for (AvailabilitySlot slot : availabilitySlots){

                if(slot.getStartTime().isBefore(time) && slot.getEndTime().isAfter(time)){
                    availabilitySlotsFilter.add(slot);
                }

            }
            return availabilitySlotsFilter;

        }
        // No param
        else{
            return availabilitySlots;
        }
    }




    // Post
    public AvailabilitySlot createAvailabilitySlot(AvailabilitySlotRequest request){
        AvailabilitySlot availabilitySlot = AvailabilitySlotMapper.toEntity(request);

        availabilitySlotCheck(availabilitySlot);

        return repository.save(availabilitySlot);
    }




    // Put
    public AvailabilitySlot updateAvailabilitySlot(Long availabilitySlotId, AvailabilitySlotRequest request){
        AvailabilitySlot availabilitySlot = repository.findById(availabilitySlotId).orElseThrow(() -> new AvailabilitySlotNotFoundException("Availability slot with id " + availabilitySlotId + " not found"));

        availabilitySlot.setRestaurantId(request.getRestaurantId());
        availabilitySlot.setDate(request.getDate());
        availabilitySlot.setStartTime(request.getStartTime());
        availabilitySlot.setEndTime(request.getEndTime());
        availabilitySlot.setCapacity(request.getCapacity());
        availabilitySlot.setSeatsAvailable(request.getSeatsAvailable());
        availabilitySlotCheck(availabilitySlot);

        return repository.save(availabilitySlot);
    }




    // Delete
    public AvailabilitySlot deleteAvailabilitySlot(Long availabilitySlotId){
        AvailabilitySlot availabilitySlot = repository.findById(availabilitySlotId).orElseThrow(() -> new AvailabilitySlotNotFoundException("Availability slot with id " + availabilitySlotId + " not found"));
        repository.deleteById(availabilitySlotId);
        return availabilitySlot;
    }
}
