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

    @GetMapping("/grades/{id}")
    public Grade one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new GradeNotFoundException(id));
    }

    @PostMapping("/grades")
    public Grade newGrade(@RequestBody Grade grade) {
        return repository.save(grade);
    }

    @GetMapping("/subjects/{id}/grades")
    public List<Grade> allGradesBySubject(@PathVariable Long id) {
        return repository.findBySubjectId(id);
    }

    @GetMapping("/students/{id}/grades")
    public List<Grade> allGradesByStudent(@PathVariable Long id) {
        return repository.findByStudentId(id);
    }

    @PutMapping("/grades/{id}")
    public Grade replaceGrade(@PathVariable Long id, @RequestBody Grade newGrade) {
        return repository.findById(id).map(grade -> {
            grade.setValue(newGrade.getValue());
            return repository.save(grade);
        }).orElseThrow(() -> new GradeNotFoundException(id));
    }
}
