package model;

import java.util.ArrayList;

/**
 * Interface that defines the functionalities for a callback related to music (songs)
 * Implemented when there is a need to update table contents in the case of a specific event
 */

public interface MusicCallback {

    /**
     * Method that is called when songs need to be updated
     * (due to an event, like when the client adds a song)
     * @param songs the songs that will overwrite the previous ones
     */

    void onMusicUpdated(ArrayList<Song> songs);

}
