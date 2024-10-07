package saw.exceptions;

public class GradeNotFoundException extends RuntimeException {
    public GradeNotFoundException(Long subjectId, Long userId) {
        super("Grade not found for subject " + subjectId + " and user " + userId);
    }
}
