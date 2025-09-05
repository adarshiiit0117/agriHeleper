package agriconnect.agriconnect.repository;

import agriconnect.agriconnect.model.Booking;
import agriconnect.agriconnect.model.User;
import agriconnect.agriconnect.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRenter(User renter);
    List<Booking> findByLender(User lender);
    List<Booking> findByEquipment(Equipment equipment);
}
