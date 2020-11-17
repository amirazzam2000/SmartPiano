package model.SongExceptions;

public class SongIdNotFoundException extends Exception {

    public SongIdNotFoundException(){
        super("Song id not found in the database");
    }
}
