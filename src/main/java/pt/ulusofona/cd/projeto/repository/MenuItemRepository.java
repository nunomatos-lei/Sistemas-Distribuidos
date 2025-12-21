package pt.ulusofona.cd.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ulusofona.cd.projeto.model.MenuItem;
import pt.ulusofona.cd.projeto.model.Restaurant;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

}
