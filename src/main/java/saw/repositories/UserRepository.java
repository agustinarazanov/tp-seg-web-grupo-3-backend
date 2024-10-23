package saw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import saw.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
