package agriconnect.agriconnect.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipmentResponse {
    private Long id;
    private String title;
    private String description;
    private double pricePerHour;
    private String mode;
    private String category;

    // Owner info
    private String ownerName;
    private String ownerEmail;
    private String ownerPhone;
    private String ownerAddress;
}
