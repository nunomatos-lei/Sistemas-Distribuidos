package pt.ulusofona.cd.projeto.mapper;

import pt.ulusofona.cd.projeto.dto.AvailabilitySlotRequest;
import pt.ulusofona.cd.projeto.dto.AvailabilitySlotResponse;
import pt.ulusofona.cd.projeto.dto.MenuItemRequest;
import pt.ulusofona.cd.projeto.dto.MenuItemResponse;
import pt.ulusofona.cd.projeto.model.AvailabilitySlot;
import pt.ulusofona.cd.projeto.model.MenuItem;

public class AvailabilitySlotMapper {

    public static AvailabilitySlot toEntity(AvailabilitySlotRequest request){
        AvailabilitySlot availabilitySlot = new AvailabilitySlot();
        availabilitySlot.setRestaurantId(request.getRestaurantId());
        availabilitySlot.setDate(request.getDate());
        availabilitySlot.setStartTime(request.getStartTime());
        availabilitySlot.setEndTime(request.getEndTime());
        availabilitySlot.setCapacity(request.getCapacity());
        availabilitySlot.setSeatsAvailable(request.getSeatsAvailable());
        return availabilitySlot;
    }

    public static AvailabilitySlotResponse toResponse(AvailabilitySlot availabilitySlot){
        AvailabilitySlotResponse response = new AvailabilitySlotResponse();
        response.setId(availabilitySlot.getId());
        response.setRestaurantId(availabilitySlot.getRestaurantId());
        response.setDate(availabilitySlot.getDate());
        response.setStartTime(availabilitySlot.getStartTime());
        response.setEndTime(availabilitySlot.getEndTime());
        response.setCapacity(availabilitySlot.getCapacity());
        response.setSeatsAvailable(availabilitySlot.getSeatsAvailable());
        return response;
    }

}
