package agriconnect.agriconnect.controller;

import agriconnect.agriconnect.model.Booking;
import agriconnect.agriconnect.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/{equipmentId}")
    public ResponseEntity<String> bookEquipment(
            @PathVariable Long equipmentId,
            Authentication authentication
    ) {
        return bookingService.bookEquipment(equipmentId, authentication);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<String> cancelBooking(
            @PathVariable Long bookingId,
            Authentication authentication
    ) {
        return bookingService.cancelBooking(bookingId, authentication);
    }

    @GetMapping("/me")
    public ResponseEntity<List<Booking>> getMyBookings(Authentication authentication) {
        return bookingService.getMyBookings(authentication);
    }

    @GetMapping("/lender")
    public ResponseEntity<List<Booking>> getLenderBookings(Authentication authentication) {
        return bookingService.getLenderBookings(authentication);
    }
}
