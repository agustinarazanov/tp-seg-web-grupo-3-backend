package saw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import saw.models.LoginRequest;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<LoginRequest, Long> {
    Optional<LoginRequest> findByEmailAndPassword(String email, String password);
}
