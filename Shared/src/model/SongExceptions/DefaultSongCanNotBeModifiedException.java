package model.SongExceptions;

public class DefaultSongCanNotBeModifiedException extends Exception {

    public DefaultSongCanNotBeModifiedException(){
        super("Default song cannot be modified.");
    }
}
