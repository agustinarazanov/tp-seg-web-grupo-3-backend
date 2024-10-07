package saw.controllers;

import org.springframework.web.bind.annotation.*;
import saw.exceptions.SubjectNotFoundException;
import saw.exceptions.UserNotFoundException;
import saw.models.Subject;
import saw.models.User;
import saw.repositories.SubjectRepository;

import java.util.List;

@RestController
public class SubjectController {
    private final SubjectRepository subjectRepository;

    public SubjectController(SubjectRepository repository) {
        this.subjectRepository = repository;
    }

    @GetMapping("/subjects")
    public List<Subject> all() {
        return subjectRepository.findAll();
    }

    @PostMapping("/subjects")
    public Subject newSubject(@RequestBody Subject subject) {
        return subjectRepository.save(subject);
    }

    @GetMapping("/subjects/{id}")
    public Subject one(@PathVariable Long id) {
        return subjectRepository.findById(id).orElseThrow(() -> new SubjectNotFoundException(id));
    }

    @PatchMapping("/subjects/{id}")
    public Subject updateSubject(@PathVariable Long id, @RequestBody Subject newSubject) {
        return subjectRepository.findById(id).map(subject -> {
            if (newSubject.getName() != null) subject.setName(newSubject.getName());
            if (newSubject.hasUsers()) newSubject.getUsers().forEach(subject::addUser);
            return subjectRepository.save(subject);
        }).orElseThrow(() -> new SubjectNotFoundException(id));
    }

    @DeleteMapping("/subjects/{id}")
    public void deleteSubject(@PathVariable Long id) {
        subjectRepository.deleteById(id);
    }

    @GetMapping("/subjects/{id}/users")
    public List<User> allUsers(@PathVariable Long id) {
        return subjectRepository.findById(id).map(Subject::getUsers).orElseThrow(() -> new SubjectNotFoundException(id));
    }

    @GetMapping("/subjects/{subjectId}/users/{userId}")
    public User oneUser(@PathVariable Long subjectId, @PathVariable Long userId) {
        return subjectRepository.findById(subjectId).flatMap(subject -> subject.getUser(userId)).orElseThrow(() -> new UserNotFoundException(userId));
    }
}
