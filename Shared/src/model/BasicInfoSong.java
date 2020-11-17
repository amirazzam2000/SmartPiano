package model;

import java.io.Serializable;
import java.sql.ResultSet;

/**
 *This class is a simplified version of the Song class. This class is used to only send the title of a song and the id.
 *That way, instead of storing the other information of a user, we will only have the basic information to show the
 *titles of the songs. If more information about the song is needed, since this class also hold the id of a song, it
 * will be easy to get that information from the Song class.
 *
 * @author Sonia Leal
 * @version %I% %G%
 *
 */
public class BasicInfoSong implements Serializable {
    private int id;
    private String title;

    /**
     * This method creates an object that contains the basic information of a song
     * @param id id of a song
     * @param title title of a song
     */
    public BasicInfoSong(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "BasicInfoSong{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
