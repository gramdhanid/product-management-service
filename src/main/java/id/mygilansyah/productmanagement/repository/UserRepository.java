package id.mygilansyah.productmanagement.repository;

import id.mygilansyah.productmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
