package id.mygilansyah.productmanagement.repository;

import id.mygilansyah.productmanagement.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {


//    @Query("select r from Roles r where (r.roleCode = ?1 or r.roleName = ?2) and r.deleted = ?3")
//    Optional<Roles> findByRoleCodeOrRoleNameAndDeleted(String roleCode, String roleName, Boolean deleted);

    Optional<Roles> findTopByRoleCodeOrRoleNameAndDeleted(String roleCode, String roleName, Boolean deleted);

    @Query("select r from Roles r where r.id = ?1 and r.deleted = ?2")
    Optional<Roles> findByIdAndDeleted(Long id, Boolean deleted);

    @Query("select r from Roles r where r.deleted = ?1")
    List<Roles> findAllByIdAndDeleted(Boolean deleted);

}
