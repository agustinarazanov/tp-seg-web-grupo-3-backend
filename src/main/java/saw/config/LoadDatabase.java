package saw.config;

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
            gradeRepository.deleteAll();
            subjectRepository.deleteAll();
            userRepository.deleteAll();

            // --- USERS ---
            User juan = new User("Juan", "student", "jperez@gmail.com", passwordEncoder.encode("juanElMejorNoMeHackeen"));
            log.info("Preloading {}", userRepository.save(juan));

            User carlos = new User("Carlos", "teacher", "crodriguez@gmail.com", passwordEncoder.encode("carlospass1234"));
            log.info("Preloading {}", userRepository.save(carlos));

            User pepe = new User("Pepe", "teacher", "pramirez@gmail.com", passwordEncoder.encode("starwars"));
            log.info("Preloading {}", userRepository.save(pepe));

            // --- SUBJECTS ---
            Subject math = new Subject("matemática");
            math.setTeacher(carlos);
            math.addStudent(juan);
            log.info("Preloading {}", subjectRepository.save(math));

            Subject physics = new Subject("física");
            physics.setTeacher(carlos);
            physics.addStudent(juan);
            log.info("Preloading {}", subjectRepository.save(physics));

            Subject literature = new Subject("literatura");
            literature.setTeacher(pepe);
            log.info("Preloading {}", subjectRepository.save(literature));

            // --- GRADES ---
            Grade mathGrade = new Grade(8, juan, math);
            log.info("Preloading {}", gradeRepository.save(mathGrade));

            Grade pyhsicsGrade = new Grade(9, juan, physics);
            log.info("Preloading {}", gradeRepository.save(pyhsicsGrade));
        };
    }
}
