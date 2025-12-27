package pt.ulusofona.cd.projeto.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "notifications", uniqueConstraints = @UniqueConstraint(columnNames = {"reservation_id", "event_type"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @NotNull
    @Column(name = "reservation_id", nullable = false, columnDefinition = "UUID")
    private UUID reservationId;

    @NotBlank
    @Size(max = 50)
    @Column(name = "event_type", nullable = false, length = 50)
    private String eventType;

    @NotBlank
    @Email
    @Size(max = 255)
    @Column(name = "recipient", nullable = false, length = 255)
    private String recipient;

    @NotBlank
    @Size(max = 20)
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "sent_at", nullable = false, updatable = false)
    private Instant sentAt;

    @PrePersist
    protected void onCreate() {
        sentAt = Instant.now();
    }
}
