package model.UserExceptions;

public class PasswordUpperException extends Exception{
    public PasswordUpperException() {
        super("the password must contain Upper case characters");
    }
}
