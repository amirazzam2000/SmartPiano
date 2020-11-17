package model.UserExceptions;

public class UserNameNotFoundException extends Exception {

    public UserNameNotFoundException(){
        super("User name not found in the database");
    }

}
