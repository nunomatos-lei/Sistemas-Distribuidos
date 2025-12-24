package pt.ulusofona.cd.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ulusofona.cd.projeto.model.MenuItem;
import pt.ulusofona.cd.projeto.model.Restaurant;

import java.util.List;
import java.util.UUID;

public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {

    public List<MenuItem> findAllByRestaurantId(UUID restaurantId);

}
