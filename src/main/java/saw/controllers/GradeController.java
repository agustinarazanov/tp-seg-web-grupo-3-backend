package saw.controllers;

import org.springframework.web.bind.annotation.*;
import saw.exceptions.GradeNotFoundException;
import saw.models.Grade;
import saw.repositories.GradeRepository;

import java.util.List;

@RestController
public class GradeController {
    private final GradeRepository repository;

    public GradeController(GradeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/grades")
    public List<Grade> all() {
        return repository.findAll();
    }

    @PostMapping("/grades")
    public Grade newGrade(@RequestBody Grade grade) {
        return repository.save(grade);
    }

    @GetMapping("/subjects/{id}/grades")
    public List<Grade> allGradesBySubject(@PathVariable Long id) {
        return repository.findBySubjectId(id);
    }

    @GetMapping("/subjects/{subjectId}/users/{userId}/grades")
    public Grade oneByUser(@PathVariable Long subjectId, @PathVariable Long userId) {
        return repository.findBySubjectIdAndUserId(subjectId, userId).orElseThrow(() -> new GradeNotFoundException(subjectId, userId));
    }

    @PutMapping("/subjects/{subjectId}/users/{userId}/grades")
    public Grade replaceGrade(@PathVariable Long subjectId, @PathVariable Long userId, @RequestBody Grade newGrade) {
        return repository.findBySubjectIdAndUserId(subjectId, userId).map(grade -> {
            grade.setValue(newGrade.getValue());
            return repository.save(grade);
        }).orElseThrow(() -> new GradeNotFoundException(subjectId, userId));
    }
}
