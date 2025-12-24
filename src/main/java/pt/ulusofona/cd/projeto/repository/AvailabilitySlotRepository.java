package pt.ulusofona.cd.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ulusofona.cd.projeto.model.AvailabilitySlot;

import java.util.List;
import java.util.UUID;

public interface AvailabilitySlotRepository extends JpaRepository<AvailabilitySlot, UUID> {

    public List<AvailabilitySlot> findAllByRestaurantId(UUID restaurantId);

}
