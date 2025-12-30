package pt.ulusofona.cd.projeto.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pt.ulusofona.cd.projeto.dto.ReservationDto;



import java.util.UUID;


@FeignClient(name = "reservation-service", url = "http://reservation-service:8082")
public interface ReservationClient {
    @GetMapping("/api/v1/reservations/{reservationId}")
    ReservationDto getReservationById(@PathVariable UUID reservationId);
}
