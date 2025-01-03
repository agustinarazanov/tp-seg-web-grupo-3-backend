package saw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import saw.exceptions.MissingPrivilegesException;
import saw.exceptions.UserNotFoundException;
import saw.models.Subject;
import saw.models.User;
import saw.repositories.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/current-user")
    public User getCurrentUser(@AuthenticationPrincipal Jwt jwt) {
        Optional<User> foundUser = repository.findByEmail(jwt.getSubject());

        if (foundUser.isPresent()) {
            User user = foundUser.get();
            user.setPassword(null);

            return user;
        }

        throw new UserNotFoundException();
    }

    @GetMapping("/users")
    public List<User> all() {
        return repository.findAll();
    }

    @Transactional
    @PostMapping("/users")
    public void newUser(@RequestBody User user) {
        Long id = (Long) entityManager.createNativeQuery("SELECT next_val FROM user_seq;").getSingleResult();
        entityManager.createNativeQuery("UPDATE user_seq SET next_val = next_val + 1;").executeUpdate();
        entityManager.createNativeQuery(
                "INSERT INTO user (id, email, password, name, role) VALUES ('"
                        + id + "','"
                        + user.getEmail() + "','"
                        + passwordEncoder.encode(user.getPassword()) + "','"
                        + user.getName() + "','"
                        + user.getRole() + "');", User.class).executeUpdate();
    }

    @GetMapping("/users/{id}")
    public User one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/users/{id}")
    public User changeRol(@PathVariable Long id, @RequestBody User newUser) {
        if (Objects.equals(newUser.getRole(), "admin")) {
            throw new MissingPrivilegesException();
        }
        return repository.findById(id).map(user -> {
            user.setRole(newUser.getRole());
            return repository.save(user);
        }).orElseThrow(() -> new UserNotFoundException(id));
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/users/{userId}/subjects")
    public List<Subject> getUserSubjects(@PathVariable Long userId) {
        // Create query to join User and Subject_students tables
        Query query = entityManager.createNativeQuery("SELECT s.* FROM subject s JOIN subject_students ss ON s.id = ss.subject_id WHERE ss.students_id = " + userId + ";", Subject.class);
        return query.getResultList();
    }

}
