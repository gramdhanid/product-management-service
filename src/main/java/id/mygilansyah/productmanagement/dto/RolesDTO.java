package id.mygilansyah.productmanagement.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

public class RolesDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class RequetRolesDTO {
        private Long id;
        @Length(max = 10, message = "Role Name max length is 10 character")
        private String roleName;
        @Length(max = 10, message = "Role Code max length is 10 character")
        private String roleCode;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class ResponseRolesDTO {
        private Long id;
        private String rolename;
        private String roleCode;
    }

}
