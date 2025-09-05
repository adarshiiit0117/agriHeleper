package agriconnect.agriconnect.response;

import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken;
    private boolean status;
    private String message;

    // Extra user info (optional, useful after login/register)
    private String fullName;
    private String email;
    private String phoneNumber;
}
