package pt.ulusofona.cd.projeto.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "reservation_id", columnDefinition = "UUID")
    private UUID reservationId;

    @Column(name = "restaurant_id", columnDefinition = "UUID")
    private UUID restaurantId;

    @NotBlank
    @Size(max = 255)
    @Column(name = "customer_name", nullable = false, length = 255)
    private String customerName;

    @NotBlank
    @Email
    @Size(max = 255)
    @Column(name = "customer_email", nullable = false, length = 255)
    private String customerEmail;

    @Size(max = 20)
    @Column(name = "tax_number", unique = true, length = 20)
    private String taxNumber;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "currency", nullable = false, length = 7)
    private String currency;
}
