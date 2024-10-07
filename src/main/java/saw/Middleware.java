package saw;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import saw.exceptions.GradeNotFoundException;
import saw.exceptions.SubjectNotFoundException;
import saw.exceptions.UserNotFoundException;

@RestControllerAdvice
public class Middleware {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userNotFoundHandler(UserNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(SubjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String subjectNotFoundHandler(SubjectNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(GradeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String gradeNotFoundHandler(GradeNotFoundException e) {
        return e.getMessage();
    }
}
