package model.SongExceptions;

public class UserDoesNotHavePermissionException extends Exception {

    public UserDoesNotHavePermissionException(){super("The user does not have permission to delete the song.");}

}
