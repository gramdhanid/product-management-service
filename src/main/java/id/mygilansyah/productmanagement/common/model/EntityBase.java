package id.mygilansyah.productmanagement.common.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@MappedSuperclass
@NoArgsConstructor
@ToString
@Getter
@Setter
public class EntityBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 50)
    private Long id;
    @Column(length = 100)
    private String description;
}
