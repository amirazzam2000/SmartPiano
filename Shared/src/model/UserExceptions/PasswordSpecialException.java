package model.UserExceptions;

public class PasswordSpecialException extends Exception{
    public PasswordSpecialException() {
        super("the password must contain at least 1 spacial characters");
    }
}
