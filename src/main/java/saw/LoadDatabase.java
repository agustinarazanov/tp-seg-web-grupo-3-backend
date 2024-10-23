package saw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initDatabase(
            UserRepository userRepository,
            SubjectRepository subjectRepository,
            GradeRepository gradeRepository
    ) {
        return args -> {
            // --- USERS ---
            User juan = new User((long)1, "juan", "student", "juan@gmail.com", passwordEncoder.encode("123456"));

            Optional<User> foundPepe = userRepository.findById((long)1);
            if (foundPepe.isEmpty()) {
                log.info("Preloading {}", userRepository.save(juan));
            }

            User carlos = new User((long)2, "carlos", "teacher", "carlos@gmail.com", passwordEncoder.encode("carlospass1234"));
            log.info("Preloading {}", userRepository.save(carlos));

            // --- SUBJECTS ---
            Subject math = new Subject((long)1, "matemática");
            math.addUser(carlos);
            math.addUser(juan);
            log.info("Preloading {}", subjectRepository.save(math));

            // --- GRADES ---
            Grade mathGrade = new Grade((long)1, 8, juan, math);
            log.info("Preloading {}", gradeRepository.save(mathGrade));
        };
    }
}
