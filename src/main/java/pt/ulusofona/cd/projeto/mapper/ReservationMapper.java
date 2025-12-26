package pt.ulusofona.cd.projeto.mapper;

import pt.ulusofona.cd.projeto.dto.ReservationRequest;
import pt.ulusofona.cd.projeto.dto.ReservationResponse;
import pt.ulusofona.cd.projeto.model.Reservation;

public class ReservationMapper {

    public static Reservation toEntity(ReservationRequest dto) {
        Reservation p = new Reservation();
        p.setRestaurantId(dto.getRestaurantId());
        p.setCustomerName(dto.getCustomerName().trim());
        p.setCustomerEmail(dto.getCustomerEmail().trim());
        p.setSeatsReserved(dto.getSeatsReserved());
        p.setScheduledDay(dto.getScheduledDay());
        p.setScheduledTime(dto.getScheduledTime());
        return p;
    }

    public static ReservationResponse toResponse(Reservation entity) {
        ReservationResponse r = new ReservationResponse();
        r.setId(entity.getId());
        r.setRestaurantId(entity.getRestaurantId());
        r.setAvailabilitySlotId(entity.getAvailabilitySlotId());
        r.setCustomerName(entity.getCustomerName());
        r.setCustomerEmail(entity.getCustomerEmail());
        r.setSeatsReserved(entity.getSeatsReserved());
        r.setStatus(entity.getStatus());
        r.setScheduledDay(entity.getScheduledDay());
        r.setScheduledTime(entity.getScheduledTime());
        return r;
    }

}
