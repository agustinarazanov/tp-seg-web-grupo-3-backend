package saw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import saw.models.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
