package Server;

import Controller.FriendsCallback;
import Controller.LoginUserExceptions.EmptyCredentialsException;
import Controller.MainController;
import Controller.UserCallback;
import model.BasicInfoSong;
import model.Config;
import model.Song;
import model.SongExceptions.SongWrongFormatException;
import model.User;
import model.UserExceptions.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class controls the communication with the Server
 *
 *
 * @author Amir Azzam
 * @author Sonia leal
 * @author felipe perez
 * @author Nicole leser
 *
 */
public class CommunicationServer extends Thread {
    private final DataOutputStream dos;
    private final ObjectOutputStream oos;
    private final DataInputStream dis;
    private final ObjectInputStream ois;
    private ServerCallbacks serverCallbacks;
    private ServerCallbacks serverCallbacks2;
    private static ServerCallbacks serverCallbacks3;
    private User user;
    private static FriendsCallback friendsCallback;
    private static UserCallback userCallback;
    private boolean running;

    /**
     * this is the constructor for this class, it creates the socket and
     * initializes the input/output streams.
     * @param config the configuration file that contains the port
     *               information and the address of the host
     * @throws IOException in case any error happened while creating the
     * streams
     */
    public CommunicationServer(Config config) throws IOException {

        Socket socket = new Socket(config.getAddress(), config.getPort_client());

        dos = new DataOutputStream(socket.getOutputStream());
        oos = new ObjectOutputStream(socket.getOutputStream());

        ois = new ObjectInputStream(socket.getInputStream());
        dis = new DataInputStream(socket.getInputStream());
    }

    public User getUser() {
        return user;
    }

    public static void setFriendsCallback(FriendsCallback friendsCallback) {
        CommunicationServer.friendsCallback = friendsCallback;
    }

    public static void setUserCallback(UserCallback userCallback){
        CommunicationServer.userCallback = userCallback;
    }

    public static void setFriendSongsCallback(ServerCallbacks userCallback){
        CommunicationServer.serverCallbacks3 = userCallback;
    }
    /**
     * starts the connection with the server
     */
    public void startConnection(){
        running = true;
        start();
    }

    /**
     * disconnect from the server
     */
    public void disconnectConnection(){
        running = false;
        interrupt();
    }

    @Override
    public void run () {
        try {
            System.out.println("hello");
            while (running) {
                if(dis.available() > 0) {

                    int protocol = 0;
                    try {
                        protocol = dis.readInt();
                    }catch (EOFException ignored){}

                    switch (protocol) {
                        case Config.LOGIN_CLIENT_PROTOCOL:
                            receiveUserLogin();
                            break;
                        case Config.REGISTER_CLIENT_PROTOCOL:
                            receiveUserRegistration();
                            break;
                        case Config.SHARE_SONG_PROTOCOL:
                            System.out.println("Song Saved!");
                            break;
                        case Config.GET_SONGS:
                            receiveSongs();
                            break;
                        case Config.PLAY_SONG:
                            receiveSongById();
                            break;
                        case Config.GET_FRIENDS:
                            receiveFriends();
                            break;
                        case Config.ADD_FRIENDS:
                            receiveAddedFriend();
                            break;
                        case Config.DELETE_CURRENT_USER:
                            currentUserDeleted();
                            break;
                        case Config.GET_FRIENDS_SONGS:
                            getUserSongs();
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method receives the songs by a specific user, and then passes the
     * information to the the registered {@link ServerCallbacks} and displays an
     * error message in case something went wrong
     *
     * @see ServerCallbacks
     */
    private void getUserSongs() {
        try {
            Object o = ois.readObject();
            if (o instanceof Exception){
                MainController.showErrorMessage(((Exception) o).getMessage());
            }else{
                serverCallbacks3.onUserSongsReceived((ArrayList<BasicInfoSong>) o);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method acknowledges that the user has been deleted and the
     * request have been successful
     */
    private void currentUserDeleted() {
        user = null;
        MainController.showErrorMessage("User Deleted " +
                "Successfully");
        userCallback.onUserDeleted();
    }

    /**
     * this method receives the updated list of the user's friend after they
     * add a friend and then pass it to the registered
     * {@link FriendsCallback} and and displays an error message in case something went wrong
     *
     * @see FriendsCallback
     * @see User
     */
    private void receiveAddedFriend() {
        try {
            Object receivedObject = ois.readObject();
            if (receivedObject instanceof ArrayList) {
                System.out.println("Friend added!");
                friendsCallback.onFriendsUpdated((ArrayList<User>) receivedObject);
            }else if (receivedObject instanceof Exception){
                MainController.showErrorMessage(((Exception) receivedObject).getMessage());
            }
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * receives the requested friends list, and sends it to the main
     * controller
     *
     * @see MainController
     * @see User
     */
    private void receiveFriends() {
        try{
            ArrayList<User> friends = (ArrayList<User>) ois.readObject();
            MainController.receiveFriends(friends);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * receives the requested song and passes it to the registered {@link ServerCallbacks}
     * and displays an error message in case something went wrong
     */
    private void receiveSongById() {
        try {
            Object receivedObject =ois.readObject();
            if (receivedObject instanceof  Song) {
                Song song = (Song) receivedObject;
                serverCallbacks2.onSongReceived(song);
            }else if (receivedObject instanceof Exception){
                MainController.showErrorMessage(((Exception) receivedObject).getMessage());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * receives three lists of songs (public songs, friends songs, and own
     * songs) and then passes them to the {@link MainController}
     *
     * @see MainController
     * @see BasicInfoSong
     */
    private void receiveSongs() {
        try {
            ArrayList<BasicInfoSong> publicSongs =
                    (ArrayList<BasicInfoSong>) ois.readObject();
            ArrayList<BasicInfoSong> friendsSongs =
                    (ArrayList<BasicInfoSong>) ois.readObject();
            ArrayList<BasicInfoSong> ownSongs =
                    (ArrayList<BasicInfoSong>) ois.readObject();
            MainController.receiveSongs(publicSongs,friendsSongs,ownSongs);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    /**
     * This method obtains the response from the server when a user tries to log in.
     * then passes the information to the registered {@link ServerCallbacks}
     *
     */
    private void receiveUserLogin() {
        try {
            Object receivedObject = ois.readObject();
            if (receivedObject instanceof User) {
                this.user = (User) receivedObject;
                System.out.println("got it " + user.getCode());
                serverCallbacks.onUserReceived();
            } else if (receivedObject instanceof Exception) {
                MainController.showErrorMessage(((Exception) receivedObject).getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * this method receives the response from the server when the client is
     * attempting to register a new user then passes the information to the registered {@link ServerCallbacks}
     */
    private void receiveUserRegistration(){
        try {
            Object receivedObject = ois.readObject();
            if (receivedObject instanceof User) {
                this.user = (User) receivedObject;
                System.out.println("got it " + user.getCode());
                serverCallbacks.onUserReceived();
            } else if (receivedObject instanceof Exception) {
                MainController.showErrorMessage(((Exception) receivedObject).getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * this methods send a request to the server to register a new user
     * @param user the user to be registered
     * @throws WrongFormatException if the user entered an email with a wrong
     * format
     * @throws EmptyFieldException if the user left one of the fields empty
     */
    public void registerUser(User user, ServerCallbacks serverCallbacks) throws WrongFormatException,
            EmptyFieldException, PasswordShortException, PasswordUpperException,
            PasswordLowerException, PasswordSpecialException, PasswordDigitException {

        this.serverCallbacks = serverCallbacks;
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(user.getEmail());


        int upCount = 0, loCount = 0, digit = 0, special = 0;
        if(user.getPassword().length()>=8){
            for(int i =0;i<user.getPassword().length();i++){
                char c = user.getPassword().charAt(i);
                if(Character.isUpperCase(c)){
                    upCount++;
                }
                if(Character.isLowerCase(c)){
                    loCount++;
                }
                if(Character.isDigit(c)){
                    digit++;
                }
                if(!Character.isLetter(c) && !Character.isDigit(c)){
                    special++;
                }
            }
            if(!(special>=1&&loCount>=1&&upCount>=1&&digit>=1)){
                if(upCount == 0)
                    throw new PasswordUpperException();
                else if(loCount == 0)
                    throw new PasswordLowerException();
                else if(special == 0)
                    throw new PasswordSpecialException();
                else if (digit == 0)
                    throw new PasswordDigitException();

            }

        }
        else{
            throw new PasswordShortException();
        }

        try {
            if (user.getNickname() != null && !user.getNickname().equals("") && user.getPassword() != null && !user.getPassword().equals("")) {
                if(mat.matches()){
                System.out.println("sending user");
                dos.writeInt(Config.REGISTER_CLIENT_PROTOCOL);
                oos.writeObject(user);
                }
                else {
                    throw new WrongFormatException();
                }
            }
            else{
              throw new EmptyFieldException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method sends a user to be verified in the server side
     * @param usernameEmail a String that can either be the email or the username (which are two valid options to login)
     * @param password the password of the client
     * @throws EmptyCredentialsException this exception is thrown if at least there's one of the credentials empty
     */
    public void loginUser(String usernameEmail, String password,
                          ServerCallbacks serverCallbacks) throws EmptyCredentialsException {
        this.serverCallbacks = serverCallbacks;
        //Check that credentials are not empty
        if(usernameEmail.equals("") || password.equals("")){
            throw new EmptyCredentialsException();
        }

        System.out.println("Sending user login");
        try {
            dos.writeInt(Config.LOGIN_CLIENT_PROTOCOL);
            dos.writeUTF(usernameEmail);
            dos.writeUTF(password);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * this method sends a song to the server so it can be saved in the
     * database
     * @param song the song to be saved
     */
    public void shareSong(Song song) throws SongWrongFormatException {
        if(!song.getTitle().equals("") && !song.getFile().equals("") && this.user != null){
            song.setUserCode(this.user.getCode());
            System.out.println("sending user");
            try {
                dos.writeInt(Config.SHARE_SONG_PROTOCOL);
                oos.writeObject(song);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            throw new SongWrongFormatException();
        }
    }

    /**
     * this method sends a request to the server to get the user's songs, the
     * public songs and the user friend's songs.
     */
    public void sendGetSongsRequest(){
        try {
            dos.writeInt(Config.GET_SONGS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method send a request to the server to get all the information
     * of a song given it's id
     * @param songId the id of the requested song
     * @param callbacks the class to which the information should be directed
     *                 after it being received
     * @see ServerCallbacks
     * @see Song
     *
     */
    public void playSongById(int songId, ServerCallbacks callbacks){
        this.serverCallbacks2 = callbacks;
        try {
            dos.writeInt(Config.PLAY_SONG);
            dos.writeInt(songId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method sends a request to the server asking to get a list of all
     * the friends of the current user.
     */
    public void requestUserFriends(){
        try {
            dos.writeInt(Config.GET_FRIENDS);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * tthis method sends a request to the server to add the specified user
     * to the friends list of the current user
     * @param friendId the user to be added as a friend
     */
    public void requestAddFriendByCode(String friendId){
        try {
            dos.writeInt(Config.ADD_FRIENDS);
            dos.writeUTF(friendId);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method sends a request to the server to get all the songs of a
     * specified friend of the current user
     * @param friendId the id of the friend who's songs have been requested
     */
    public void requestFriendSongsByCode(String friendId){
        try {
            dos.writeInt(Config.GET_FRIENDS_SONGS);
            dos.writeUTF(friendId);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * this sends a message notifying the server that the current user have
     * logged out
     */
    public void logout(){
        this.user = null;
        try {
            dos.writeInt(Config.LOGOUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method sends a request to the server to delete the current user.
     */
    public void deleteCurrentUser(){
        this.user = null;
        try {
            dos.writeInt(Config.DELETE_CURRENT_USER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * this method tells the server to disconnect
     */
    public void disconnect(){
        try {
            dos.writeInt(Config.DISCONNECT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
