package id.mygilansyah.productmanagement.model;

import id.mygilansyah.productmanagement.common.model.ReferenceBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "master_product")
public class Product extends ReferenceBase {
    private String name;
    private BigDecimal price;

}
