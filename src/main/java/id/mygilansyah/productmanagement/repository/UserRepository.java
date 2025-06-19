package id.mygilansyah.productmanagement.repository;

import id.mygilansyah.productmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findTopByUsernameOrEmailAndDeleted(String username, String email, Boolean deleted);

    @Query("select u from User u where u.username = ?1 and u.deleted = ?2")
    Optional<User> findByUsernameAndDeleted(String username, Boolean deleted);


}
