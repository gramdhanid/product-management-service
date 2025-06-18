package id.mygilansyah.productmanagement.model;

import id.mygilansyah.productmanagement.common.model.ReferenceBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "master_user")
public class User extends ReferenceBase {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String country;
    private Boolean active;
    private String loginStatus;
    private Integer wrongPasswordCount;
    private LocalDateTime lastLogin;
    private LocalDateTime tokenExpiryDate;
}
