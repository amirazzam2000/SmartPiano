package model.UserExceptions;

public class UsernameAlreadyExistsException extends Exception{
    public UsernameAlreadyExistsException(){
        super("The username already exists in the database");
    }
}
