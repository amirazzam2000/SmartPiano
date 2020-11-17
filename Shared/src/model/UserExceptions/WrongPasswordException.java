package model.UserExceptions;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
        super("Wrong Password");
    }
}
