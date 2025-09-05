package agriconnect.agriconnect.repository;

import agriconnect.agriconnect.model.Equipment;
import agriconnect.agriconnect.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    List<Equipment> findByCategory(Category category);
}
