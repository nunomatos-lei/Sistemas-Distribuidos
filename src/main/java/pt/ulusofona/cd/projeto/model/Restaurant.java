package pt.ulusofona.cd.projeto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Column(name = "city", nullable = false, length = 120)
    private String city;

    @Column(name = "country", nullable = false, length = 120)
    private String country;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "email", nullable = false, length = 120)
    private String email;
}
