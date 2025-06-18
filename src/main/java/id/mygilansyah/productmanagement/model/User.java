package id.mygilansyah.productmanagement.model;

import id.mygilansyah.productmanagement.common.model.ReferenceBase;
import jakarta.persistence.*;
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
    @Column(length = 10)
    private String username;
    private String password;
    @Column(length = 50)
    private String fullName;
    private String email;
    @Column(length = 15)
    private String phoneNumber;
    @Column(length = 100)
    private String address;
    @Column(length = 20)
    private String city;
    @Column(length = 20)
    private String country;
    private Boolean active;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles role;
    private Boolean loginStatus;
    private Integer wrongPasswordCount;
    private LocalDateTime lastLogin;
    private LocalDateTime tokenExpiryDate;
}
