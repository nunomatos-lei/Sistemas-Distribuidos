package pt.ulusofona.cd.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ulusofona.cd.projeto.model.AvailabilitySlot;

import java.util.List;

public interface AvailabilitySlotRepository extends JpaRepository<AvailabilitySlot, Long> {

    public List<AvailabilitySlot> findAllByRestaurantId(Long restaurantId);

}
