package saw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import saw.models.Grade;

import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findBySubjectId(Long id);
    Optional<Grade> findBySubjectIdAndUserId(Long subjectId, Long userId);
    Optional<Grade> findById(Long id);
}
