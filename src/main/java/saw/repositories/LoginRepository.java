package saw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import saw.models.Login;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Optional<Login> findByEmailAndPassword(String email, String password);
}
