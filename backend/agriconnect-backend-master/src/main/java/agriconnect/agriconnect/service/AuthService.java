package agriconnect.agriconnect.service;

import agriconnect.agriconnect.model.User;
import agriconnect.agriconnect.repository.UserRepository;
import agriconnect.agriconnect.request.RegisterRequest;
import agriconnect.agriconnect.request.LoginRequest;
import agriconnect.agriconnect.request.ForgotPasswordRequest;
import agriconnect.agriconnect.response.AuthResponse;
import agriconnect.agriconnect.config.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * Register a new user
     */
    public ResponseEntity<String> registerUser(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body("Email already exists");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCountry(request.getCountry());
        user.setState(request.getState());
        user.setPincode(request.getPincode());
        user.setAddress(request.getAddress());
        user.setRole("USER");

        userRepository.save(user);
        return ResponseEntity.status(201).body("User registered successfully");
    }

    /**
     * Login and generate JWT
     */
    public AuthResponse login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        AuthResponse response = new AuthResponse();

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getEmail(), "USER");
                response.setAccessToken(token);
                response.setStatus(true);
                response.setMessage("Login successful");

                response.setFullName(user.getFullName());
                response.setEmail(user.getEmail());
                response.setPhoneNumber(user.getPhoneNumber());
                return response;
            }
        }

        response.setStatus(false);
        response.setMessage("Invalid email or password");
        return response;
    }

    /**
     * Forgot Password - reset with email + new password
     */
    public ResponseEntity<String> forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + request.getEmail()));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Password reset successful");
    }

    /**
     * Get user profile by email
     */
    public ResponseEntity<User> getUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return ResponseEntity.ok(user);
    }

    /**
     * Update user profile
     */
    public ResponseEntity<String> updateUserProfile(String email, User updatedUser) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        user.setFullName(updatedUser.getFullName());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        user.setCountry(updatedUser.getCountry());
        user.setState(updatedUser.getState());
        user.setPincode(updatedUser.getPincode());
        user.setAddress(updatedUser.getAddress());

        userRepository.save(user);
        return ResponseEntity.ok("User profile updated successfully");
    }
}

