package pt.ulusofona.cd.projeto.mapper;

import pt.ulusofona.cd.projeto.dto.ReservationRequest;
import pt.ulusofona.cd.projeto.dto.ReservationResponse;
import pt.ulusofona.cd.projeto.model.Reservation;

public class ReservationMapper {
    public static Reservation toEntity(ReservationRequest dto) {
        Reservation p = new Reservation();
        p.setRestaurantID(dto.getRestaurantID());
        p.setCustomerName(dto.getCustomerName().trim());
        p.setCustomerEmail(dto.getCustomerEmail().trim());
        p.setPartySize(dto.getPartySize());
        p.setStatus(dto.getStatus() != null ? dto.getStatus() : "PENDING");
        return p;
    }

    public static ReservationResponse toResponse(Reservation entity) {
        ReservationResponse r = new ReservationResponse();
        r.setReservationID(entity.getReservationID());
        r.setRestaurantID(entity.getRestaurantID());
        r.setCustomerName(entity.getCustomerName());
        r.setCustomerEmail(entity.getCustomerEmail());
        r.setPartySize(entity.getPartySize());
        r.setStatus(entity.getStatus());
        return r;
    }
}
