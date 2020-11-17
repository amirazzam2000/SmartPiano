package model.SongExceptions;

public class SongWrongFormatException extends Throwable {
    public SongWrongFormatException() {
        super("Song format invalid");
    }
}
