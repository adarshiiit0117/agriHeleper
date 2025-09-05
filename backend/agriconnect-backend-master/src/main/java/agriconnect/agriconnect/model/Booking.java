package agriconnect.agriconnect.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    private User renter; // user who books

    @ManyToOne
    @JoinColumn(name = "lender_id", nullable = false)
    private User lender; // equipment owner

    private LocalDateTime bookingTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
