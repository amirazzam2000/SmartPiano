package model.UserExceptions;

public class EmptyFieldException extends Exception {
    public EmptyFieldException() {
        super("you can't leave the fields empty");
    }
}
