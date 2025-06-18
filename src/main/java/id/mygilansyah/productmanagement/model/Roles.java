package id.mygilansyah.productmanagement.model;

import id.mygilansyah.productmanagement.common.model.ReferenceBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sys_master_roles")
public class Roles extends ReferenceBase {
    @Column(length = 10)
    private String roleName;
    @Column(length = 10)
    private String roleCode;
}
