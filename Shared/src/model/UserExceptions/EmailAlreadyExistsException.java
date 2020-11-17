package model.UserExceptions;

public class EmailAlreadyExistsException extends Exception{
    public EmailAlreadyExistsException() {
        super("email already exists in the database");
    }
}
