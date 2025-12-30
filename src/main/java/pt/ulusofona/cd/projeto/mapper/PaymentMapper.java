package pt.ulusofona.cd.projeto.mapper;

import pt.ulusofona.cd.projeto.dto.PaymentRequest;
import pt.ulusofona.cd.projeto.dto.PaymentResponse;
import pt.ulusofona.cd.projeto.model.Payment;

public class PaymentMapper {
    public static Payment toEntity(PaymentRequest dto) {
        Payment p = new Payment();
        p.setReservationId(dto.getReservationId());
        p.setRestaurantId(dto.getRestaurantId());
        p.setCustomerName(dto.getCustomerName().trim());
        p.setCustomerEmail(dto.getCustomerEmail().trim());
        p.setTaxNumber(dto.getTaxNumber().trim());
        p.setPrice(dto.getPrice());
        p.setCurrency(dto.getCurrency().trim());
        return p;
    }

    public static PaymentResponse toResponse(Payment entity) {
        PaymentResponse r = new PaymentResponse();
        r.setId(entity.getId());
        r.setReservationId(entity.getReservationId());
        r.setRestaurantId(entity.getRestaurantId());
        r.setCustomerName(entity.getCustomerName());
        r.setCustomerEmail(entity.getCustomerEmail());
        r.setTaxNumber(entity.getTaxNumber());
        r.setPrice(entity.getPrice());
        r.setCurrency(entity.getCurrency());
        return r;
    }
}
