package pt.ulusofona.cd.projeto.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "restaurant_id", columnDefinition = "UUID")
    private UUID restaurantId;

    @Column(name = "availability_slot_id", columnDefinition = "UUID")
    private UUID availabilitySlotId;

    @NotBlank
    @Size(max = 255)
    @Column(name = "customer_name", nullable = false, length = 255)
    private String customerName;

    @NotBlank
    @Email
    @Size(max = 255)
    @Column(name = "customer_email", nullable = false, length = 255)
    private String customerEmail;

    @Min(1)
    @Column(name = "seats_reserved")
    private int seatsReserved;

    @Column(name = "status", nullable = false)
    private String status = "PENDING";

    @Column(name = "scheduled_day", nullable = false, updatable = false)
    private LocalDate scheduledDay;

    @Column(name = "scheduled_time", nullable = false, updatable = false)
    private LocalTime scheduledTime;
}
