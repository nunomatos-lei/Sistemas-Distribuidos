package pt.ulusofona.cd.projeto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulusofona.cd.projeto.dto.AvailabilitySlotRequest;
import pt.ulusofona.cd.projeto.exception.AvailabilitySlotNotFoundException;
import pt.ulusofona.cd.projeto.exception.InvalidAvailabilitySlotException;
import pt.ulusofona.cd.projeto.exception.MenuItemNotFoundException;
import pt.ulusofona.cd.projeto.mapper.AvailabilitySlotMapper;
import pt.ulusofona.cd.projeto.model.AvailabilitySlot;
import pt.ulusofona.cd.projeto.repository.AvailabilitySlotRepository;
import pt.ulusofona.cd.projeto.repository.RestaurantRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvailabilitySlotService {

    // Const
    private final AvailabilitySlotRepository AvailabilitySlotrepository;
    private final RestaurantRepository restaurantRepository;




    // Func
    public void availabilitySlotCheck(AvailabilitySlot availabilitySlot){
        // Numbers check
        if (availabilitySlot.getSeatsAvailable() < 0 || availabilitySlot.getCapacity() < 0){
            throw new InvalidAvailabilitySlotException("Seats available or capacity cannot be negative");
        }
        if (availabilitySlot.getSeatsAvailable() > availabilitySlot.getCapacity()){
            throw new InvalidAvailabilitySlotException("Seats available cannot be greater than capacity");
        }

        // Mixed check
        if(availabilitySlot.getStartTime().isAfter(availabilitySlot.getEndTime())){
            throw new InvalidAvailabilitySlotException("Availability slots start time cannot be after the end time");
        }

        // Past check
        LocalDateTime slotDateStartTime = LocalDateTime.of(availabilitySlot.getDate(), availabilitySlot.getStartTime());
        if(slotDateStartTime.isBefore(LocalDateTime.now())){
            throw new InvalidAvailabilitySlotException("Availability slots cannot be in the past");
        }

        // Overlap check
        List<AvailabilitySlot> availabilitySlotList = AvailabilitySlotrepository.findAll();
        for (AvailabilitySlot slot : availabilitySlotList){

            if(availabilitySlot.getId() != null && availabilitySlot.getId().equals(slot.getId())){
                continue;
            }

            boolean startTimeCheck = availabilitySlot.getStartTime().isAfter(slot.getStartTime()) && availabilitySlot.getStartTime().isBefore(slot.getEndTime());
            boolean endTimeCheck = availabilitySlot.getEndTime().isAfter(slot.getStartTime()) && availabilitySlot.getEndTime().isBefore(slot.getEndTime());

            boolean dateCheck = availabilitySlot.getDate().equals(slot.getDate());
            boolean overlap = startTimeCheck || endTimeCheck;
            boolean swallow = availabilitySlot.getStartTime().isBefore(slot.getStartTime()) && availabilitySlot.getEndTime().isAfter(slot.getEndTime());
            boolean same = slot.getStartTime().equals(availabilitySlot.getStartTime()) && slot.getEndTime().equals(availabilitySlot.getEndTime());

            if(dateCheck && (same || swallow || overlap)){
                throw new InvalidAvailabilitySlotException("Availability slots cannot overlap");
            }

        }

    }




    // Get
    public List<AvailabilitySlot> getAllAvailabilitySlots(){
        return AvailabilitySlotrepository.findAll();
    }

    public AvailabilitySlot getAvailabilitySlotsById(UUID availabilitySlotId){
        return AvailabilitySlotrepository.findById(availabilitySlotId).orElseThrow(() -> new AvailabilitySlotNotFoundException("Availability slot with id " + availabilitySlotId + " not found"));
    }

    public List<AvailabilitySlot> getAvailabilitySlotsByRestaurantId(UUID restaurantId, LocalDate date, LocalTime time){

        List<AvailabilitySlot> availabilitySlots = AvailabilitySlotrepository.findAllByRestaurantId(restaurantId);

        if(availabilitySlots.isEmpty()){
            return availabilitySlots;
        }

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
    @Transactional
    public AvailabilitySlot createAvailabilitySlot(AvailabilitySlotRequest request){
        restaurantRepository.findById(request.getRestaurantId()).orElseThrow(() -> new MenuItemNotFoundException("Restaurant with id " + request.getRestaurantId() + " not found"));

        AvailabilitySlot availabilitySlot = AvailabilitySlotMapper.toEntity(request);

        availabilitySlotCheck(availabilitySlot);

        return AvailabilitySlotrepository.save(availabilitySlot);
    }

    @Transactional
    public AvailabilitySlot updateSeats(UUID availabilitySlotId, int seatUpdate){
        AvailabilitySlot availabilitySlot =  AvailabilitySlotrepository.findById(availabilitySlotId).orElseThrow(() -> new AvailabilitySlotNotFoundException("Availability slot with id " + availabilitySlotId + " not found"));

        int seats = availabilitySlot.getSeatsAvailable() + seatUpdate;
        if (seats < 0){
            throw new RuntimeException();
        }

        availabilitySlot.setSeatsAvailable(seats);
        return AvailabilitySlotrepository.save(availabilitySlot);
    }




    // Put
    @Transactional
    public AvailabilitySlot updateAvailabilitySlot(UUID availabilitySlotId, AvailabilitySlotRequest request){
        AvailabilitySlot availabilitySlot = AvailabilitySlotrepository.findById(availabilitySlotId).orElseThrow(() -> new AvailabilitySlotNotFoundException("Availability slot with id " + availabilitySlotId + " not found"));
        restaurantRepository.findById(request.getRestaurantId()).orElseThrow(() -> new MenuItemNotFoundException("Restaurant with id " + request.getRestaurantId() + " not found"));

        availabilitySlot.setRestaurantId(request.getRestaurantId());
        availabilitySlot.setDate(request.getDate());
        availabilitySlot.setStartTime(request.getStartTime());
        availabilitySlot.setEndTime(request.getEndTime());
        availabilitySlot.setCapacity(request.getCapacity());
        availabilitySlot.setSeatsAvailable(request.getSeatsAvailable());
        availabilitySlotCheck(availabilitySlot);

        return AvailabilitySlotrepository.save(availabilitySlot);
    }




    // Delete
    @Transactional
    public AvailabilitySlot deleteAvailabilitySlot(UUID availabilitySlotId){
        AvailabilitySlot availabilitySlot = AvailabilitySlotrepository.findById(availabilitySlotId).orElseThrow(() -> new AvailabilitySlotNotFoundException("Availability slot with id " + availabilitySlotId + " not found"));

        try{
            AvailabilitySlotrepository.deleteById(availabilitySlotId);
        }catch (RuntimeException e){
            throw new InvalidAvailabilitySlotException("Cannot delete availability slot with reservations");
        }

        return availabilitySlot;
    }
}
