package Controller;

/**
 * this interface is used as an observer to notify whoever implements it
 * about the changes that has happened on the currently playing song
 */
public interface MusicCallbacks {
    /**
     * this method is called when the song is done
     */
    void onSongDone();
}
