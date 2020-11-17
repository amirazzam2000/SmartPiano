package model;

import java.io.Serializable;

/**
 * This class is used to store information (id, name, file, user code, and if the song is private or public) of a song.
 * It hold all the information of a song.
 *
 * @author Sonia Leal
 * @version %I% %G%

 */
public class Song implements Serializable {

    private int id;
    private String title;
    private String file;
    private float min;
    private String userCode;
    private boolean isPublic;
    private int timesPlayed;

    /**
     * This constructor creates a song given all of the information
     * @param id id of the song
     * @param title  title of the song
     * @param file file of the song
     * @param min duration of the song
     * @param userCode id of the user who uploaded the song
     * @param isPublic whether the song is public or private
     * @param timesPlayed how many times this song has been played
     */
    public Song(int id, String title, String file, float min, String userCode, boolean isPublic, int timesPlayed) {
        this.id = id;
        this.title = title;
        this.file = file;
        this.min = min;
        this.userCode = userCode;
        this.isPublic = isPublic;
        this.timesPlayed = timesPlayed;
    }

    /**
     * This construcor is used to create a song without the id nor timesPlayed
     * @param title title of the song
     * @param file file of the song
     * @param min duration of the song
     * @param userCode id of the user who uploaded the song
     * @param isPublic whether the song is public or private
     */
    public Song(String title, String file, float min, String userCode, boolean isPublic) {
        this.title = title;
        this.file = file;
        this.min = min;
        this.userCode = userCode;
        this.isPublic = isPublic;
        this.timesPlayed = 0;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public int getTimesPlayed() {
        return timesPlayed;
    }

    public void setTimesPlayed(int timesPlayed) {
        this.timesPlayed = timesPlayed;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

}
