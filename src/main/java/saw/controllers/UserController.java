package saw.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
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

    @GetMapping("/current-user")
    public User getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof Jwt jwt) {
                String email = jwt.getClaim("sub");
                return repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(0L));
            }
        }

        throw new Exception("No user logged in");
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
