package agriconnect.agriconnect.service;

import agriconnect.agriconnect.model.*;
import agriconnect.agriconnect.repository.BookingRepository;
import agriconnect.agriconnect.repository.EquipmentRepository;
import agriconnect.agriconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;

    public ResponseEntity<String> bookEquipment(Long equipmentId, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> renterOpt = userRepository.findByEmail(username);
        Optional<Equipment> equipmentOpt = equipmentRepository.findById(equipmentId);

        if (renterOpt.isEmpty() || equipmentOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid user or equipment");
        }

        User renter = renterOpt.get();
        Equipment equipment = equipmentOpt.get();

        if (!equipment.isAvailable()) {
            return ResponseEntity.badRequest().body("Equipment not available");
        }

        Booking booking = Booking.builder()
                .equipment(equipment)
                .renter(renter)
                .lender(equipment.getOwner())
                .bookingTime(LocalDateTime.now())
                .status(BookingStatus.BOOKED)
                .build();

        equipment.markAsBooked();
        equipmentRepository.save(equipment);
        bookingRepository.save(booking);

        return ResponseEntity.ok("Equipment booked successfully!");
    }

    public ResponseEntity<String> cancelBooking(Long bookingId, Authentication authentication) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);

        if (bookingOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Booking not found");
        }

        Booking booking = bookingOpt.get();

        // Only renter can cancel
        if (!booking.getRenter().getEmail().equals(authentication.getName())) {
            return ResponseEntity.status(403).body("You are not authorized to cancel this booking");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        booking.getEquipment().markAsAvailable();
        equipmentRepository.save(booking.getEquipment());
        bookingRepository.save(booking);

        return ResponseEntity.ok("Booking cancelled successfully");
    }

    public ResponseEntity<List<Booking>> getMyBookings(Authentication authentication) {
        Optional<User> userOpt = userRepository.findByEmail(authentication.getName());
        if (userOpt.isEmpty()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(bookingRepository.findByRenter(userOpt.get()));
    }

    public ResponseEntity<List<Booking>> getLenderBookings(Authentication authentication) {
        Optional<User> userOpt = userRepository.findByEmail(authentication.getName());
        if (userOpt.isEmpty()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(bookingRepository.findByLender(userOpt.get()));
    }

    // ✅ New method: Mark booking as completed
    public ResponseEntity<String> markAsCompleted(Long bookingId, Authentication authentication) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);

        if (bookingOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Booking not found");
        }

        Booking booking = bookingOpt.get();

        // Only lender can mark as completed
        if (!booking.getLender().getEmail().equals(authentication.getName())) {
            return ResponseEntity.status(403).body("You are not authorized to complete this booking");
        }

        if (!booking.getStatus().equals(BookingStatus.BOOKED)) {
            return ResponseEntity.badRequest().body("Only booked services can be marked as completed");
        }

        booking.setStatus(BookingStatus.COMPLETED);
        booking.getEquipment().markAsAvailable(); // ✅ Make equipment available again
        equipmentRepository.save(booking.getEquipment());
        bookingRepository.save(booking);

        return ResponseEntity.ok("Booking marked as completed successfully");
    }
}
