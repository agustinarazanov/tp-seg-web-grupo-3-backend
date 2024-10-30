package saw.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/subjects")
    public List<Subject> all(@RequestParam(name = "studentId", required = false) String studentId, @RequestParam(name = "teacherId", required = false) String teacherId) {

        if (studentId != null) {
            Query query = entityManager.createNativeQuery("select s.* from subject s join subject_students on id = subject_id where students_id = " + studentId + ";");
            return query.getResultList();
        }

        if (teacherId != null) {
            return subjectRepository.findByTeacherId(Long.parseLong(teacherId));
        }

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
            if (newSubject.hasStudents()) newSubject.getStudents().forEach(subject::addStudent);
            return subjectRepository.save(subject);
        }).orElseThrow(() -> new SubjectNotFoundException(id));
    }

    @DeleteMapping("/subjects/{id}")
    public void deleteSubject(@PathVariable Long id) {
        subjectRepository.deleteById(id);
    }

    @GetMapping("/subjects/{id}/students")
    public List<User> allStudents(@PathVariable Long id) {
        return subjectRepository.findById(id).map(Subject::getStudents).orElseThrow(() -> new SubjectNotFoundException(id));
    }

    @GetMapping("/subjects/{id}/teacher")
    public User teacher(@PathVariable Long id) {
        return subjectRepository.findById(id).map(Subject::getTeacher).orElseThrow(() -> new SubjectNotFoundException(id));
    }

    @GetMapping("/subjects/{subjectId}/students/{userId}")
    public User oneStudent(@PathVariable Long subjectId, @PathVariable Long userId) {
        return subjectRepository.findById(subjectId).flatMap(subject -> subject.getStudent(userId)).orElseThrow(() -> new UserNotFoundException(userId));
    }
}
