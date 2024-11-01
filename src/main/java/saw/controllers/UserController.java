package saw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import saw.exceptions.UserNotFoundException;
import saw.models.User;
import saw.repositories.UserRepository;

import java.util.List;

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

    @GetMapping("/users")
    public List<User> all() {
        return repository.findAll();
    }

    @Transactional
    @PostMapping("/users")
    public void newUser(@RequestBody User user) {
        entityManager.createNativeQuery("INSERT INTO user (email, password, name, role) VALUES ('" 
            + user.getEmail() + "','" + passwordEncoder.encode(user.getPassword()) + "','" + user.getName() + "','" + user.getRole() + "');",
            User.class).executeUpdate();
    }

    @GetMapping("/users/{id}")
    public User one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/users/{id}")
    public User replaceUser(@PathVariable Long id, @RequestBody User newUser) {
        return repository.findById(id).map(user -> {
            user.setName(newUser.getName());
            user.setRole(newUser.getRole());
            return repository.save(user);
        }).orElseGet(() -> repository.save(newUser));
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
