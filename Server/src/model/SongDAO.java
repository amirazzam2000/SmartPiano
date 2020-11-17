package model;

import model.SongExceptions.DefaultSongCanNotBeModifiedException;
import model.SongExceptions.SongIdNotFoundException;
import model.StatisticsExceptions.StatisticsEmptyException;
import model.SongExceptions.UserDoesNotHavePermissionException;
import model.UserExceptions.UserNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *This class manages all the connections to the database in order get, delete and update information about the
 * songs table of the database. Some of the methods of this class include obtaining information give a song id and/or
 * a user id.
 *
 * @author Sonia Leal
 * @version %I% %G%
 *
 * @see ConnectorDB
 * @see ResultSet
 * @see Config
 * @see Song
 * @see BasicInfoSong
 */
public class SongDAO {
    private static ConnectorDB connector;
    private static MusicCallback callback;

    /**
     * This method allows to connect to the database as well as initialize an object of this class
     * @param configuration contains the information that allows to connect to the database
     * @see Config
     * @see ConnectorDB
     */
    public SongDAO(Config configuration) {
        connector = new ConnectorDB(configuration.getUser(), configuration.getPassword(),
                configuration.getName(),
                configuration.getPort_db());

    }

    /**
     * a default constructor to be able to have an instance of this class and
     * access it's methods from other classes
     */
    public SongDAO(){

    }

    public synchronized static void setCallback(MusicCallback callback) {
        SongDAO.callback = callback;
    }

    /**
     * This method is used to check if the user exists in the database.
     * @param id id of the user
     * @return whether the user exists or not
     * @throws UserNotFoundException user has not been found in the database
     */
    private synchronized boolean isUser(String id) throws UserNotFoundException{
        ResultSet rs = connector.selectQuery("SELECT * FROM users WHERE code " +
                "like '" + id + "'");
        try {
            if(!rs.next()) {
                throw new UserNotFoundException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * This method adds a new song to the database.
     * @param song Includes the information about the song that will be added
     * @throws UserNotFoundException user has not been found in the database
     */
    public synchronized void addSong(Song song) throws UserNotFoundException {
        if (isUser(song.getUserCode())) {
            connector.insertQuery("INSERT INTO songs (title, file, min, user_code, is_public, times_played)" +
                    "values ( '" + song.getTitle() + "','" + song.getFile() + " ', '" + song.getMin() + "', '" + song.getUserCode() + "' , " + song.isPublic() + " , 0 )");

            if(callback != null){
             callback.onMusicUpdated(getAllSongs());
            }
        }

    }


    /**
     * Private method that gets a song by id, if it exists, without having to check which user is demanding this action.
     * The primarily use of this method is for purposes such as retrieving information for the statistics and the
     * top 5 songs which are two functionalities that all users have access to.
     * @param idSong id of the song
     * @return Song which contains all the information of the song
     * @throws SongIdNotFoundException thrown when the id of the demanded song is not found in the database
     */
    public synchronized Song getSongById(int idSong) throws SongIdNotFoundException {
        ResultSet resultSet = connector.selectQuery("SELECT * FROM songs WHERE song_id =" + idSong );

        try {
            if(resultSet != null && resultSet.next()){
                String title = resultSet.getString("title");
                String file = resultSet.getString("file");
                float min = resultSet.getFloat("min");
                String userCode = resultSet.getString("user_code");
                boolean isPublic = resultSet.getBoolean("is_public");
                int timesPlayed = resultSet.getInt("times_played");
                return new Song(idSong, title, file, min, userCode, isPublic, timesPlayed);
            }else{
                throw new SongIdNotFoundException();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    /**
     * This method returns a Song object given a song id. NOTE: This method also increments the time this song has been played
     * @param idSong the id of the song that the user is looking for
     * @return a Song object which contains all the information regarding the song selected by id
     * @throws SongIdNotFoundException this exception is thrown in case the id of the song is not found in the databas
     */
    public synchronized Song playSongById(int idSong) throws SongIdNotFoundException {

        ResultSet resultSet = connector.selectQuery("SELECT * FROM songs WHERE song_id =" + idSong );
        try{
            if(resultSet != null && resultSet.next()){
                String title = resultSet.getString("title");
                String file = resultSet.getString("file");
                float min = resultSet.getFloat("min");
                String userCode = resultSet.getString("user_code");
                boolean isPublic = resultSet.getBoolean("is_public");
                int timesPlayed = resultSet.getInt("times_played");
                timesPlayed++;
                connector.updateQuery("UPDATE songs SET times_played = " + timesPlayed + " WHERE song_id = " + idSong);
                updateStats(min);
                getStatisticsTotalSongs();
                return new Song(idSong, title, file, min, userCode, isPublic, timesPlayed);
            }else{
                throw new SongIdNotFoundException();
            }
        } catch (SQLException | StatisticsEmptyException e) {
            e.printStackTrace();
        }


        return null;
    }

    /**
     * Method that can only be accessed from this class that is used to update the statistics table.
     * If it is the first song played in this day, then a new row will be inserted. Otherwise, the row with
     * the corresponding date will be updated.
     * @param numMin Minutes of the song that is played
     */
    private synchronized void updateStats(float numMin){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();
        System.out.println(dtf.format(localDate));
        ResultSet resultSet = connector.selectQuery("SELECT * FROM stats WHERE date LIKE '" + dtf.format(localDate) + "' ");

        try{

            if(resultSet !=null && resultSet.next()){
                String date = resultSet.getString("date");
                int numSongs = resultSet.getInt("num_songs");
                float numMinutes = resultSet.getFloat("num_minutes");
                numSongs++;
                numMinutes += numMin;
                connector.updateQuery("UPDATE stats SET num_songs = " + numSongs + ", num_minutes =" + numMinutes +
                        "WHERE date = '" + date + "' ");

            }else{
                connector.insertQuery("INSERT INTO stats (date, num_songs, num_minutes) " +
                        "VALUES ( '" + dtf.format(localDate)+ "', 1 , " + numMin + " )");
                System.out.println();

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * This method returns the basic information of a song given a song id, it does NOT return the file of the song
     * nor the other characteristic of a song.
     * @param id the id of the song that the user is looking for
     * @return a BasicInfoSong object which contains all the information regarding the song selected by id
     * @throws SongIdNotFoundException this exception is thrown in case the id of the song is not found in the database
     * @see BasicInfoSong
     */
    private synchronized BasicInfoSong getBasicInfoSongById(int id) throws SongIdNotFoundException {
        ResultSet resultSet = connector.selectQuery("SELECT * FROM songs WHERE song_id =" + id );
        try{
            if(resultSet != null && resultSet.next()){
                String title = resultSet.getString("title");
                return new BasicInfoSong(id, title);
            }else{
                throw new SongIdNotFoundException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method used to verify if the user has permission on a specific song
     * @param songId id of the song
     * @param userId id of the user
     * @throws UserNotFoundException user was not found in the database
     * @throws UserDoesNotHavePermissionException user does not have permissions
     * @throws SongIdNotFoundException song was not found in the database
     */
    public synchronized void checkUserPermissionsByUserIdAndSongId(int songId,
                                                       String userId) throws UserNotFoundException, UserDoesNotHavePermissionException, SongIdNotFoundException {
        if(isUser(userId)){
            ResultSet resultSet = connector.selectQuery("SELECT * FROM songs WHERE song_id =" + songId );
            try{
                if(resultSet != null && resultSet.next()){
                    String userCode = resultSet.getString("user_code");
                    if(!userId.equals( userCode)){ throw new UserDoesNotHavePermissionException();
                    }
                }else{
                    throw new SongIdNotFoundException();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is used to delete a song.
     * IMPORTANT: If this method wants to be used to delete a song from the user side, first it must be checked that
     *            the user has permission to perform the desired action by using the
     *            checkUserPermissionsByUserIdAndSongId(int songId, int userId) method
     * @throws SongIdNotFoundException exception thrown when the id of the song is not found
     * @throws UserNotFoundException user has not been found in the database
     * @throws DefaultSongCanNotBeModifiedException exception thrown if either the user or server wants to delete a default song
     */
    public synchronized void deleteSongById(int songId) throws SongIdNotFoundException,
            DefaultSongCanNotBeModifiedException {
        if(songId == 1 || songId == 2 || songId == 3 ) throw new DefaultSongCanNotBeModifiedException();
        ResultSet resultSet = connector.selectQuery("SELECT * FROM songs WHERE song_id =" + songId );
        try{
            if(resultSet != null && resultSet.next()){
                connector.deleteQuery("DELETE FROM songs WHERE song_id = " + songId);
            }else{
                throw new SongIdNotFoundException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method allows the rename a song title
     * IMPORTANT: If this method wants to be used to rename a song from the user side, first it must be checked that
     *            the user has permission to perform the desired action by using the
     *            checkUserPermissionsByUserIdAndSongId(int songId, int userId) method
     * @param songId id of the song that wants to be renamed
     * @param newTitle new title to replace current title
     * @throws SongIdNotFoundException song id not found in the database
     * @throws DefaultSongCanNotBeModifiedException exception thrown if either the user or server wants to rename a default song
     */
    public synchronized void renameSongTitleById(int songId, String newTitle) throws SongIdNotFoundException, DefaultSongCanNotBeModifiedException {
        if(songId == 1 || songId == 2 || songId == 3 ) throw new DefaultSongCanNotBeModifiedException();
        ResultSet resultSet = connector.selectQuery("SELECT * FROM songs WHERE song_id =" + songId );
        try{
            if(resultSet != null && resultSet.next()){
                connector.updateQuery("UPDATE songs SET title = '" + newTitle + "' WHERE song_id = " + songId);
            }else{
                throw new SongIdNotFoundException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method allows the user to change the privacy of a song (set if the song is public or private)
     * IMPORTANT: If this method wants to be used to rename a song from the user side, first it must be checked that
     *            the user has permission to perform the desired action by using the
     *            checkUserPermissionsByUserIdAndSongId(int songId, int userId) method
     * @param songId id of the song
     * @return if the permission of the song have been changed successfully or not
     * @throws SongIdNotFoundException thrown when the id of the song has not been found in the database
     * @throws UserDoesNotHavePermissionException thrown if the user is not the one who uploaded that song
     * @throws UserNotFoundException user has not been found in the database
     * @throws DefaultSongCanNotBeModifiedException exception thrown if either the user or server wants to change permission of a default song
     */
    public synchronized void changePermissionBySongId(int songId) throws SongIdNotFoundException, DefaultSongCanNotBeModifiedException {
        if(songId == 1 || songId == 2 || songId == 3 ) throw new DefaultSongCanNotBeModifiedException();
            ResultSet resultSet = connector.selectQuery("SELECT * FROM songs WHERE song_id =" + songId );
            try{
                if(resultSet != null && resultSet.next()){
                    connector.updateQuery("UPDATE songs SET is_public = !is_public WHERE song_id = " + songId);

                }else{
                    throw new SongIdNotFoundException();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

    /**
     * This method returns all the basic information (only title and id) of the public songs of one user
     * @param id id of the user
     * @return an array of all the basic information of the public songs of that user
     * @throws UserNotFoundException user has not been found in the database
     * @see BasicInfoSong
     * @see ArrayList
     */
    public synchronized ArrayList<BasicInfoSong> getPublicSongsByUserId(String id) throws UserNotFoundException {
        ArrayList<BasicInfoSong> songs = new ArrayList<>();
        if(isUser(id)){
            ResultSet resultSet = connector.selectQuery("SELECT * FROM songs " +
                    "WHERE user_code like '" + id + "'");
            try {
                while (resultSet.next()) {
                    if(resultSet.getBoolean("is_public")) songs.add(getBasicInfoSongById(resultSet.getInt("song_id")));
                }
            }catch (SQLException | SongIdNotFoundException e) {
                e.printStackTrace();
            }
        }

        return songs;
    }

    /**
     * This method gets all the public songs in an array list of type
     * {@link  BasicInfoSong} which only has the title and the id
     * @return an array list of all the public songs
     * @see BasicInfoSong
     * @see ArrayList
     *
     */
    public synchronized ArrayList<BasicInfoSong> getAllPublicSongs(){
        ResultSet resultSet = connector.selectQuery("SELECT * FROM songs WHERE is_public IS TRUE ");
        ArrayList<BasicInfoSong> songs = new ArrayList<>();
        try {
            while (resultSet.next()) {
                songs.add(getBasicInfoSongById(resultSet.getInt("song_id")));
            }
        }catch (SQLException | SongIdNotFoundException e) {
            e.printStackTrace();
        }
        return songs;
    }

    /**
     * This method gets all the songs in an array list of type
     * {@link  BasicInfoSong} which only has the title and the id
     * @return an array list of all the public songs
     * @see BasicInfoSong
     * @see ArrayList
     *
     */
    public synchronized ArrayList<Song> getAllSongs(){
        ResultSet resultSet = connector.selectQuery("SELECT * FROM songs ");
        ArrayList<Song> songs = new ArrayList<>();
        try {
            while (resultSet.next()) {
                songs.add(getSongById(resultSet.getInt("song_id")));
            }
        }catch (SQLException | SongIdNotFoundException e) {
            e.printStackTrace();
        }
        return songs;
    }

    /**
     * This method allows to get all the basic info of songs (public and private) of one user
     * @param id id of the user
     * @return an array list of type BasicInfoSong with all the songs of a user
     * @throws UserNotFoundException user has not been found in the database
     * @see BasicInfoSong
     * @see ArrayList
     */
    public synchronized ArrayList<BasicInfoSong> getAllSongsByUserId(String id) throws UserNotFoundException {
        ArrayList<BasicInfoSong> songs = new ArrayList<>();
        if(isUser(id)){
            ResultSet resultSet = connector.selectQuery("SELECT * FROM songs " +
                    "WHERE user_code like '" + id + "'");
            try {
                while (resultSet.next()) {
                    songs.add(getBasicInfoSongById(resultSet.getInt("song_id")));
                }
            }catch (SQLException | SongIdNotFoundException e) {
                e.printStackTrace();
            }
        }
        return songs;
    }

    /**
     * This method obtains the top 5 most played songs which is determined by the number of times a song has been played
     * @return returns an {@link ArrayList} of {@link BasicInfoSong} of the 5 songs that have been played
     * the most. The  {@link ArrayList} is ordered by times played in descending order. If there aren't five song in
     * the database, then it will return null in all the positions (if there are no songs) or just a few null positions
     * if there are a less than 5 songs.
     * @see BasicInfoSong
     * @see ArrayList
     */
    public synchronized ArrayList<Song> getTopSongs(){
        ArrayList<Song> top = new ArrayList<>();
        ResultSet resultSet = connector.selectQuery("SELECT * FROM songs WHERE times_played > 0 ORDER BY times_played DESC LIMIT 5");
        try {
            while (resultSet.next()) {
                top.add(getSongById(resultSet.getInt("song_id")));
            }
        }catch (SQLException | SongIdNotFoundException e) {
            e.printStackTrace();
        }
        return top;
    }


    /**
     * This method to get all the statistics in an {@link ArrayList} of {@link Statistics}.
     * NOTE: if there are no stats yet in the database, the ArrayList will be empty.
     * @throws StatisticsEmptyException it is thrown when there are no statistics in the database
     * @see ArrayList
     * @see Statistics
     * @return an {@link ArrayList} with all the statistics
     */
    public synchronized ArrayList<Statistics> getStatistics() throws StatisticsEmptyException {
        ArrayList<Statistics> stats = new ArrayList<>();
        getStatisticsTotalSongs();
        try {
            ResultSet resultSet = connector.selectQuery("SELECT * FROM stats");
            while (resultSet.next()) {
                String date = resultSet.getString("date");
                int numSongs = resultSet.getInt("num_songs");
                int numMinutes = resultSet.getInt("num_minutes");

                stats.add(new Statistics(date, numSongs, numMinutes));
            }

            if(stats.size() == 0) throw new StatisticsEmptyException();
        } catch (SQLException e1) {
            System.out.println("Error a MySQL");
        }
        return stats;
    }

    /**
     * this method updates the totals of the statistics
     * @throws StatisticsEmptyException it is thrown when there are no statistics in the database
     * @see ArrayList
     * @see Statistics
     */
    public synchronized void getStatisticsTotalSongs() throws StatisticsEmptyException {

        try {
            ResultSet resultSet = connector.selectQuery("select sum" +
                    "(num_songs) s from stats");
            Statistics.setTotalSongs(resultSet.getInt("s"));

            resultSet = connector.selectQuery("select sum(num_minutes) m from stats;");
            Statistics.setTotalMin(resultSet.getDouble("m"));


        } catch (SQLException e1) {
            System.out.println("Error a MySQL");
        }
    }


}