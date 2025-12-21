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
        availabilitySlot.setRestaurant_id(request.getRestaurant_id());
        availabilitySlot.setDate(request.getDate());
        availabilitySlot.setStart_time(request.getStart_time());
        availabilitySlot.setEnd_time(request.getEnd_time());
        availabilitySlot.setCapacity(request.getCapacity());
        availabilitySlot.setSeats_available(request.getSeats_available());
        return availabilitySlot;
    }

    public static AvailabilitySlotResponse toResponse(AvailabilitySlot availabilitySlot){
        AvailabilitySlotResponse response = new AvailabilitySlotResponse();
        response.setId(availabilitySlot.getId());
        response.setRestaurant_id(availabilitySlot.getRestaurant_id());
        response.setDate(availabilitySlot.getDate());
        response.setStart_time(availabilitySlot.getStart_time());
        response.setEnd_time(availabilitySlot.getEnd_time());
        response.setCapacity(availabilitySlot.getCapacity());
        response.setSeats_available(availabilitySlot.getSeats_available());
        return response;
    }

}
