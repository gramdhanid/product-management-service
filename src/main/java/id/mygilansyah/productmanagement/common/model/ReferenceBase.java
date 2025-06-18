package id.mygilansyah.productmanagement.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReferenceBase extends AuditableBase {
    private static final long serialVersionUID = 1L;
    @Version
    @Column(length = 10)
    private Integer version;
    private Boolean deleted;

    @PreUpdate
    private void preUpdate() {
        if (this.version == null) {
            this.version = 0;
        }
        this.version = this.version + 1;
    }
}
