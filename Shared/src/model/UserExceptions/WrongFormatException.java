package model.UserExceptions;

public class WrongFormatException extends Exception {
    public WrongFormatException() {
        super("the email is invalid");
    }
}
