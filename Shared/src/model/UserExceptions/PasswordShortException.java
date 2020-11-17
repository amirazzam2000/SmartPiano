package model.UserExceptions;

public class PasswordShortException extends Exception{
    public PasswordShortException() {
        super("the password must be at least 8 characters");
    }
}
