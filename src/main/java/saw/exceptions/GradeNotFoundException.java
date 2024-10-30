package saw.exceptions;

public class GradeNotFoundException extends RuntimeException {
    public GradeNotFoundException(Long id) {
        super("Grade not found with id " + id);
    }
}
