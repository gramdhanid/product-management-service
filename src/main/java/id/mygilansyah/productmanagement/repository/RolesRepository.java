package id.mygilansyah.productmanagement.repository;

import id.mygilansyah.productmanagement.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
}
