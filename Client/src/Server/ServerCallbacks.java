package Server;

import model.BasicInfoSong;
import model.Song;

import java.util.ArrayList;

/**
 * this interface is used as an observer to notify whoever implements it
 * about the information received by the server
 */
public interface ServerCallbacks {
    /**
     * this method is called when a user is received by the server
     */
    void onUserReceived();

    /**
     * this method is called when the server receives a requested song
     * @param song the requested song
     */
    void onSongReceived(Song song);

    /**
     * this method is called when the user's songs are received
     * @param songs a list of all the songs made by the currently logged in user
     */
    void onUserSongsReceived(ArrayList<BasicInfoSong> songs);
}
