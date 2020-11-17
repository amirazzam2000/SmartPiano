package model.UserExceptions;

public class SameUserException extends Exception {
    public SameUserException() {
        super("you can't add yourself as a friend!");
    }
}
