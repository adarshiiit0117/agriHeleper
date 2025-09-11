package agriconnect.agriconnect.request;

import agriconnect.agriconnect.model.Category;
import agriconnect.agriconnect.model.PricingUnit;
import lombok.Data;

@Data
public class EquipmentRequest {
    private String title;
    private String description;
    private Double price;
    private PricingUnit pricingUnit;
    private Category category;
    private String mode; // optional: if you want to store rental mode
    private String country;
    private String state;
    private String pincode;
    private String address;
}
