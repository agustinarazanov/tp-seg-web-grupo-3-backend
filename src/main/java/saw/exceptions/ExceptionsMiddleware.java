package saw.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsMiddleware {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotFoundHandler(UserNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(SubjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String subjectNotFoundHandler(SubjectNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(GradeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String gradeNotFoundHandler(GradeNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(MissingPrivilegesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String missingPrivilegesHandler(MissingPrivilegesException ex) {
        return ex.getMessage();
    }
}
