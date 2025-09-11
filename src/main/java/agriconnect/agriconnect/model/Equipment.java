package agriconnect.agriconnect.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Double price;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private PricingUnit pricingUnit;

    private String country;
    private String state;
    private String pincode;
    private String address;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Builder.Default
    private boolean available = true; // stays true until booked

    // ✅ Extra helper method to mark equipment unavailable
    public void markAsBooked() {
        this.available = false;
    }

    // ✅ Extra helper method to mark equipment available again (after cancellation)
    public void markAsAvailable() {
        this.available = true;
    }
}
