package agriconnect.agriconnect.controller;

import agriconnect.agriconnect.request.LoginRequest;
import agriconnect.agriconnect.request.RegisterRequest;
import agriconnect.agriconnect.request.ForgotPasswordRequest;
import agriconnect.agriconnect.response.AuthResponse;
import agriconnect.agriconnect.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // ✅ Register a new user
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/signup")

    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return authService.registerUser(request);
    }

    // ✅ Login
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    // ✅ Forgot Password (Reset)
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        return authService.forgotPassword(request);
    }
}
