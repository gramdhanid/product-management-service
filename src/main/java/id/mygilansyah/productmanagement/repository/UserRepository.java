package id.mygilansyah.productmanagement.repository;

import id.mygilansyah.productmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.username = ?1 and u.email = ?2 and u.deleted = ?3")
    Optional<User> findByUsernameAndEmailAndDeleted(String username, String email, Boolean deleted);

}
