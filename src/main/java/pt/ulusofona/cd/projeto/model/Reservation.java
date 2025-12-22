package pt.ulusofona.cd.projeto.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
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
    private UUID reservationID;

    @Column(name = "supplier_id", columnDefinition = "UUID")
    private UUID restaurantID;

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
    @Column(name = "party_size")
    private int partySize;

    @NotBlank
    @Column(name = "status", nullable = false, length = 16)
    private String status;

    @NotNull
    @Column(name = "scheduled_at", nullable = false)
    private Instant scheduledAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
