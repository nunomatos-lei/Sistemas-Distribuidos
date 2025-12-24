package pt.ulusofona.cd.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ulusofona.cd.projeto.model.MenuItem;
import pt.ulusofona.cd.projeto.model.Restaurant;

import java.util.List;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

}
