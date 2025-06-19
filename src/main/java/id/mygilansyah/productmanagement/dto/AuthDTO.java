package id.mygilansyah.productmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class AuthDTO {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        @NotBlank(message = "Username or email cannot be blank")
        private String username;

        @NotBlank(message = "Password cannot be blank")
        private String password;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponse {
        private UserDTO.ResponseDTO user;
        private RolesDTO.ResponseRolesDTO roles;
        private String expiredLogin;
    }
}
