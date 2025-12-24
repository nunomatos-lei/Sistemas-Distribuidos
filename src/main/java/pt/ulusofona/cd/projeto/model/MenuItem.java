package pt.ulusofona.cd.projeto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "menu_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @Column(name = "restaurant_id", nullable = false)
    private UUID restaurantId;

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "currency", nullable = false, length = 7)
    private String currency;
}
