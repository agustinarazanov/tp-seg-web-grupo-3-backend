package saw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import saw.models.Grade;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findBySubjectId(Long id);
    List<Grade> findByStudentId(Long id);
}
