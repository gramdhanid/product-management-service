package id.mygilansyah.productmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class UserDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class RegistrationDTO {
        private Long id;
        @NotBlank(message = "Username cannot be empty")
        private String username;
        @NotBlank(message = "Password cannot be empty")
        private String password;
        @NotBlank(message = "Full name cannot be empty")
        private String fullName;
        @Email
        @NotBlank(message = "Email cannot be empty")
        private String email;
        @NotBlank(message = "Phone Number cannot be empty")
        private String phoneNumber;
        @NotBlank(message = "Address cannot be empty")
        private String address;
        @NotBlank(message = "City cannot be empty")
        private String city;
        @NotBlank(message = "Country cannot be empty")
        private String country;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class ResponseDTO {
        private String username;
        private String fullName;
        private String email;
        private String phoneNumber;
        private String address;
        private String city;
        private String country;
    }

}
