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

import java.util.Optional;

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
            // --- USERS ---
            Optional<User> foundPepe = userRepository.findById((long)1);
            if (foundPepe.isEmpty()) {
                User pepe = new User((long)1, "pepe", "student");
                log.info("Preloading {}", userRepository.save(pepe));
            }

            User carlos = new User((long)2, "carlos", "teacher");
            log.info("Preloading {}", userRepository.save(carlos));

            // --- SUBJECTS ---
            Subject math = new Subject((long)1, "matemática");
            math.addUser(carlos);
            math.addUser(pepe);
            log.info("Preloading {}", subjectRepository.save(math));

            // --- GRADES ---
            Grade mathGrade = new Grade((long)1, 8, pepe, math);
            log.info("Preloading {}", gradeRepository.save(mathGrade));
        };
    }
}
