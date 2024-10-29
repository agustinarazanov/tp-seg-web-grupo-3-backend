package saw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import saw.models.Subject;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByTeacherId(Long id);
}
