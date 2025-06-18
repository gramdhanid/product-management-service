package id.mygilansyah.productmanagement.common.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AuditableBase extends EntityBase {
    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(name = "create_by", length = 50)
    private String createdBy;

    @CreatedDate
    @Column(name = "create_date", length = 50)
    private LocalDateTime createdDate;

    @LastModifiedBy
    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
}
