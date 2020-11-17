package Controller.LoginUserExceptions;

/**
 * This exception is thrown if at least one of the credentials is empty.
 * @author Sonia Leal
 */
public class EmptyCredentialsException extends Throwable {
    public EmptyCredentialsException() { super("Invalid information: empty credentials."); }
}
