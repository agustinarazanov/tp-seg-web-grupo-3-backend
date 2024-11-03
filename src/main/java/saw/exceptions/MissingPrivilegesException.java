package saw.exceptions;

public class MissingPrivilegesException extends RuntimeException {
    public MissingPrivilegesException() {
        super("Missing privileges for this operation");
    }
}
