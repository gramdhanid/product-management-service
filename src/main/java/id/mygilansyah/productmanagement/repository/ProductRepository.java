package id.mygilansyah.productmanagement.repository;

import id.mygilansyah.productmanagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query("select p from Product p where p.id = ?1 and p.deleted = ?2")
    Optional<Product> findByIdAndDeleted(Long id, Boolean deleted);
}
