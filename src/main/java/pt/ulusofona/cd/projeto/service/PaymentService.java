package pt.ulusofona.cd.projeto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulusofona.cd.projeto.client.ReservationClient;
import pt.ulusofona.cd.projeto.client.RestaurantClient;
import pt.ulusofona.cd.projeto.dto.*;
import pt.ulusofona.cd.projeto.exception.InvalidPaymentException;
import pt.ulusofona.cd.projeto.exception.PaymentNotFoundException;
import pt.ulusofona.cd.projeto.mapper.PaymentMapper;
import pt.ulusofona.cd.projeto.model.Payment;
import pt.ulusofona.cd.projeto.repository.PaymentRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    // Const
    private final PaymentRepository repository;
    private final RestaurantClient restaurantClient;
    private final ReservationClient reservationClient;


    //***************  Post  ***************//
    @Transactional
    public Payment createPayment(PaymentRequest request) {

        ReservationDto reservation = reservationClient.getReservationById(request.getReservationId());
        List<MenuItemDto> menus = restaurantClient.getMenuItemByRestaurantId(request.getRestaurantId());
        float priceTotal = 0.0F;

        if (!reservation.getStatus().equals("PAID")) {
            throw new InvalidPaymentException("Reservation status is not PAID");
        }

        if (!reservation.getCustomerName().equals(request.getCustomerName()) && !reservation.getCustomerEmail().equals(request.getCustomerEmail())) {
            throw new InvalidPaymentException("Customer name or email is not match");
        }

        // como nao há menu proprio da reserva ent foi usado como se fosse buffet, assim
        if (!menus.isEmpty()) {
            for (MenuItemDto item : menus) {
                priceTotal += item.getPrice();
            }
            request.setPrice(priceTotal);
            request.setCurrency(menus.getFirst().getCurrency());
        }

        Payment payment = PaymentMapper.toEntity(request);

        return repository.save(payment);
    }




    //***************  Get  ***************//
    public List<Payment> getAllPayments() {
        return repository.findAll();
    }

    public Payment getPaymentById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));
    }




    //***************  Put  ***************//
    @Transactional
    public Payment updatePayment(UUID id, PaymentRequest paymentDetails) {
        Payment payment = getPaymentById(id);
        payment.setReservationId(paymentDetails.getReservationId());
        payment.setRestaurantId(paymentDetails.getRestaurantId());
        payment.setCustomerName(paymentDetails.getCustomerName());
        payment.setCustomerEmail(paymentDetails.getCustomerEmail());
        payment.setTaxNumber(paymentDetails.getTaxNumber());
        payment.setPrice(paymentDetails.getPrice());
        payment.setCurrency(paymentDetails.getCurrency());

        return repository.save(payment);
    }




    //***************  Delete  ***************//
    @Transactional
    public Payment deletePayment(UUID id) {
        Payment payment = getPaymentById(id);
        repository.deleteById(id);
        return payment;
    }
}
