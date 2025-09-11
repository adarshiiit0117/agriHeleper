package agriconnect.agriconnect.request;


import lombok.Data;

@Data
public class RegisterRequest {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    private String country;
    private String state;
    private String pincode;
    private String address;

}
