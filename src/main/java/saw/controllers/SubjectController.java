package saw.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import saw.exceptions.SubjectNotFoundException;
import saw.exceptions.UserNotFoundException;
import saw.models.Subject;
import saw.models.User;
import saw.repositories.SubjectRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class SubjectController {
    private final SubjectRepository subjectRepository;

    public SubjectController(SubjectRepository repository) {
        this.subjectRepository = repository;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/subjects")
    public List<Subject> all(
            @RequestParam(name = "studentId", required = false) String studentId,
            @RequestParam(name = "teacherId", required = false) String teacherId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        List<Subject> subjects;
        if (studentId != null) {
            Query query = entityManager.createNativeQuery("select s.* from subject s join subject_students on id = subject_id where students_id = " + studentId + ";");
            subjects = query.getResultList();
        } else if (teacherId != null) {
            subjects = subjectRepository.findByTeacherId(Long.parseLong(teacherId));
        } else {
            subjects = subjectRepository.findAll();
        }
        return subjects.stream()
                .filter(s -> Objects.equals(s.getTeacher().getId(), Long.parseLong(jwt.getId())))
                .toList();
    }

    @PostMapping("/subjects")
    public Subject newSubject(@RequestBody Subject subject, @AuthenticationPrincipal Jwt jwt) {
        User user = new User();
        user.setId(Long.parseLong(jwt.getId()));
        subject.setTeacher(user);
        return subjectRepository.save(subject);
    }

    @GetMapping("/subjects/{id}")
    public Subject one(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        return subjectRepository
                .findByIdAndTeacherId(id, Long.parseLong(jwt.getId()))
                .orElseThrow(() -> new SubjectNotFoundException(id));
    }

    @PatchMapping("/subjects/{id}")
    public Subject updateSubject(
            @PathVariable Long id,
            @RequestBody Subject newSubject,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return subjectRepository.findByIdAndTeacherId(id, Long.parseLong(jwt.getId())).map(subject -> {
            if (newSubject.getName() != null) subject.setName(newSubject.getName());
            if (newSubject.hasStudents()) newSubject.getStudents().forEach(subject::addStudent);
            return subjectRepository.save(subject);
        }).orElseThrow(() -> new SubjectNotFoundException(id));
    }

    @DeleteMapping("/subjects/{id}")
    public void deleteSubject(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        Optional<Subject> subject = subjectRepository.findByIdAndTeacherId(id, Long.parseLong(jwt.getId()));
        if (subject.isPresent()) {
            subjectRepository.deleteById(id);
        }
    }

    @GetMapping("/subjects/{id}/students")
    public List<User> allStudents(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        return subjectRepository
                .findByIdAndTeacherId(id, Long.parseLong(jwt.getId()))
                .map(Subject::getStudents)
                .orElseThrow(() -> new SubjectNotFoundException(id));
    }

    @GetMapping("/subjects/{id}/teacher")
    public User teacher(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        return subjectRepository
                .findByIdAndTeacherId(id, Long.parseLong(jwt.getId()))
                .map(Subject::getTeacher)
                .orElseThrow(() -> new SubjectNotFoundException(id));
    }

    @GetMapping("/subjects/{subjectId}/students/{userId}")
    public User oneStudent(
            @PathVariable Long subjectId,
            @PathVariable Long userId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return subjectRepository
                .findByIdAndTeacherId(subjectId, Long.parseLong(jwt.getId()))
                .flatMap(subject -> subject.getStudent(userId))
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
