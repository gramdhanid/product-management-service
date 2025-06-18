package id.mygilansyah.productmanagement.repository;

import id.mygilansyah.productmanagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
