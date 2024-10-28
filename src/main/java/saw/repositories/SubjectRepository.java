package saw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import saw.models.Subject;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query(value = "select * from subject join subject_students on id = subject_id where students_id like '%?1'", nativeQuery = true)
    List<Subject> findByStudentId(String id);

    List<Subject> findByTeacherId(Long id);
}
