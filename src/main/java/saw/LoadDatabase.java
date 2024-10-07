package saw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import saw.models.Grade;
import saw.models.Subject;
import saw.models.User;
import saw.repositories.GradeRepository;
import saw.repositories.SubjectRepository;
import saw.repositories.UserRepository;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    public CommandLineRunner initDatabase(
            UserRepository userRepository,
            SubjectRepository subjectRepository,
            GradeRepository gradeRepository
    ) {
        return args -> {
            User pepe = new User("pepe", "student");
            log.info("Preloading {}", userRepository.save(pepe));
            User carlos = new User("carlos", "teacher");
            log.info("Preloading {}", userRepository.save(carlos));
            Subject math = new Subject("matemática");
            math.addUser(carlos);
            math.addUser(pepe);
            log.info("Preloading {}", subjectRepository.save(math));
            Grade mathGrade = new Grade(8, pepe, math);
            log.info("Preloading {}", gradeRepository.save(mathGrade));
        };
    }
}
