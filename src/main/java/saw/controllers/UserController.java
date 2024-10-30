package saw.controllers;

import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/users")
    public List<User> all() {
        return repository.findAll();
    }

    @PostMapping("/users")
    public User newUser(@RequestBody User user) {
        return repository.save(user);
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
