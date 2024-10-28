package saw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import saw.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
