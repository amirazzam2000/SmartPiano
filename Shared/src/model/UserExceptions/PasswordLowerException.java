package model.UserExceptions;

public class PasswordLowerException extends Exception{
    public PasswordLowerException() {
        super("the password must contain lower case characters");
    }
}
