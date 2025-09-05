package agriconnect.agriconnect.service;

import agriconnect.agriconnect.model.Equipment;
import agriconnect.agriconnect.model.User;
import agriconnect.agriconnect.model.Category;
import agriconnect.agriconnect.repository.EquipmentRepository;
import agriconnect.agriconnect.repository.UserRepository;
import agriconnect.agriconnect.request.EquipmentRequest;
import agriconnect.agriconnect.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;

    public ResponseEntity<String> registerEquipment(EquipmentRequest request, String email) {
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Equipment equipment = new Equipment();
        equipment.setTitle(request.getTitle());
        equipment.setDescription(request.getDescription());
        equipment.setPrice(request.getPrice());
        equipment.setPricingUnit(request.getPricingUnit());
        equipment.setCategory(request.getCategory()); // ✅ enum
        equipment.setCountry(request.getCountry());
        equipment.setState(request.getState());
        equipment.setPincode(request.getPincode());
        equipment.setAddress(request.getAddress());
        equipment.setOwner(owner);

        equipmentRepository.save(equipment);

        return ResponseEntity.status(201).body("✅ Equipment registered successfully");
    }

    public ResponseEntity<List<Equipment>> getEquipmentsByCategory(Category category) {
        List<Equipment> equipments = equipmentRepository.findByCategory(category);
        return ResponseEntity.ok(equipments);
    }
}
