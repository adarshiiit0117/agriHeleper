package agriconnect.agriconnect.controller;

import agriconnect.agriconnect.model.Equipment;
import agriconnect.agriconnect.model.Category;
import org.springframework.security.core.Authentication;
import agriconnect.agriconnect.request.EquipmentRequest;
import agriconnect.agriconnect.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipment")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;

    @PostMapping("/register")
    public ResponseEntity<String> registerEquipment(
            @RequestBody EquipmentRequest request,
            Authentication authentication
    ) {
        return equipmentService.registerEquipment(request, authentication.getName());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Equipment>> getEquipmentsByCategory(
            @PathVariable Category category
    ) {
        return equipmentService.getEquipmentsByCategory(category);
    }
}
