package server;

import model.*;

import model.SongExceptions.DefaultSongCanNotBeModifiedException;
import model.SongExceptions.SongIdNotFoundException;
import model.UserExceptions.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import Server.CommunicationServer
;
/**
 *  ClientDedicatedServer class used for assigning each user a dedicated server
 *  for communicating with the mainServer, retrieving their songs, connecting with others, etc.
 *  @author Felipe Perez, Arcadia Youlten, Amir Azzam, Nicole Leser
 *  @version %I% %G%
 */

public class ClientDedicatedServer extends Thread {
    private final ArrayList<ClientDedicatedServer> dedicatedServers;
    private final Socket socket;
    private final UserDAO userManager;
    private final SongDAO songManager;
    private User user;

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private boolean isRunning;

    public ClientDedicatedServer (Socket socket, ArrayList<ClientDedicatedServer> dedicatedServers) {
        this.socket = socket;
        this.dedicatedServers = dedicatedServers;
        this.userManager = new UserDAO();
        this.songManager = new SongDAO();
    }

    @Override
    public void run () {

        try{
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());

            isRunning = true;
            //from data output stream, I get a number. Receive it with a data input stream
            //based on what number we get (switch), we do one thing or another

            //every time we send something, we first send an int with a data output stream
            //then we send an object (whatever it be, a User, and expection) with a data output stream

            while(isRunning) {
                switch (dataInputStream.readInt()) {

                    case Config.REGISTER_CLIENT_PROTOCOL:
                        registerClientAttempt();
                        break;

                    case Config.LOGIN_CLIENT_PROTOCOL:
                        loginUser();
                        break;
                    case Config.SHARE_SONG_PROTOCOL:
                        saveSong();
                        break;
                    case Config.DISCONNECT:
                        isRunning = false;
                        break;
                    case Config.LOGOUT:
                            this.user = null;
                        break;
                    case Config.GET_SONGS:
                        sendSongs();
                        break;
                    case Config.PLAY_SONG:
                        playSong();
                        break;
                    case Config.GET_FRIENDS:
                        sendFriends();
                        break;
                    case Config.ADD_FRIENDS:
                        addFriend();
                        break;
                    case Config.DELETE_CURRENT_USER:
                        deleteCurrentUser();
                        break;
                    case Config.GET_FRIENDS_SONGS:
                        getFriendSongs();
                        break;
                    default:
                        System.out.println("nothing for now");
                        break;
                }
            }

        } catch (IOException e) {
            dedicatedServers.remove(this);
            System.out.println("removed");
        }

            try {
                sleep(10000);
            } catch (InterruptedException e) {
            }
            finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method for sending the songs of a user.
     * The id is provided from the data input stream, sent by the client
     * sends through the data output stream the result of calling the
     * getAllSongsByUserID method
     * @see SongDAO
     * @see CommunicationServer
     */

    private void getFriendSongs() {
        try {
            String id = dataInputStream.readUTF();
            dataOutputStream.writeInt(Config.GET_FRIENDS_SONGS);
            objectOutputStream.writeObject(songManager.getAllSongsByUserId(id));
        } catch (IOException  e) {

            e.printStackTrace();
        }
        catch ( UserNotFoundException e){
            try {
                objectOutputStream.writeObject(e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * Method to delete the current user.
     * @see UserDAO
     * @see Config
     */

    private void deleteCurrentUser() {
        try {
            for (BasicInfoSong song :
                    songManager.getAllSongsByUserId(user.getCode())) {
                songManager.deleteSongById(song.getId());
            }

            userManager.deleteUserById(user.getCode());

            dataOutputStream.writeInt(Config.DELETE_CURRENT_USER);
        } catch (UserNotFoundException | DefaultSongCanNotBeModifiedException | SongIdNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to add a friend.
     * An id needs to be received through the data input stream to know which user to add
     * @see UserDAO
     * @see Config
     */

    private void addFriend() {
        try {
            String friendId = dataInputStream.readUTF();
            dataOutputStream.writeInt(Config.ADD_FRIENDS);
            try {
                userManager.addFriendById(user.getCode(), friendId);
                objectOutputStream.writeObject(userManager.getFriendsById(user.getCode()));
            }catch(UserNotFoundException | SameUserException e){
                objectOutputStream.writeObject(e);
            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Method to send all the friends of the current user.
     * @see Config
     * @see CommunicationServer
     * @see UserDAO
     */

    private void sendFriends() {
        try {
            dataOutputStream.writeInt(Config.GET_FRIENDS);
            objectOutputStream.writeObject(userManager.getFriendsById(user.getCode()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method that will initiate the playing of a song.
     * The id of the song to play is received through the data input stream
     * @see Config
     * @see CommunicationServer
     * @see SongDAO
     */

    private void playSong() {
        try {
            int songId = dataInputStream.readInt();
            try {
                songManager.playSongById(songId);
                dataOutputStream.writeInt(Config.PLAY_SONG);
                objectOutputStream.writeObject(songManager.getSongById(songId));
            } catch (SongIdNotFoundException e) {
                objectOutputStream.writeObject(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that will send all the songs corresponding to the current user.
     * @see Config
     * @see CommunicationServer
     * @see UserDAO
     */

    private void sendSongs() {
        try {

            dataOutputStream.writeInt(Config.GET_SONGS);

            //sending the public songs
            objectOutputStream.writeObject(songManager.getAllPublicSongs());

            //sending the user friend's songs
            ArrayList<User> friends =
                    userManager.getFriendsById(this.user.getCode());
            ArrayList<BasicInfoSong> friendSongs = new ArrayList<>();
            for (User u: friends) {
                friendSongs.addAll(songManager.getAllSongsByUserId(u.getCode()));
            }
            objectOutputStream.writeObject(friendSongs);

            //sending the user's own songs
            objectOutputStream.writeObject(songManager.getAllSongsByUserId(this.user.getCode()));

        } catch (IOException | UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method receives a song form the client and saves it to the
     * database
     */

    private void saveSong() {
        //send back same int that I recieved, for the user to know that was is coming next is
        //the response to registering a user
        try {
            Song song = (Song)objectInputStream.readObject();

            try {
                songManager.addSong(song);

            } catch (UserNotFoundException e) {
                e.printStackTrace();
            }

            dataOutputStream.writeInt(Config.SHARE_SONG_PROTOCOL);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * The purpose of this method is to try and register the user.
     * The user to be registered is received through the object input stream.
     * Throws an exception with information on why the user could not be registered.
     * Any exceptions sent through the object output stream
     */

    private void registerClientAttempt(){
        try{
            //send back same int that I received, for the user to know that was is coming next is
            //the response to registering a user
            dataOutputStream.writeInt(Config.REGISTER_CLIENT_PROTOCOL);

            this.user = (User) objectInputStream.readObject();

            //attempt to register the user
            userManager.registerUser(this.user);

            //send back user object if successful
            objectOutputStream.writeObject(this.user);

            //if not successful, an exception will be thrown
        } catch (EmailAlreadyExistsException | UsernameAlreadyExistsException e) {
            //send the exception back to the user to let them know what happened
            try{
                objectOutputStream.writeObject(e);
            } catch(IOException error_sending){
                error_sending.printStackTrace();
            }

        } catch(IOException | ClassNotFoundException e){
            //these exceptions are due to something going wrong with reading the object,
            //so we simply print the error for now
            e.printStackTrace();
        }
    }

    /**
     * The purpose of this method is to try and login the user.
     * The username and password is received from the user attempting to login.
     * we then get the userID back from the userManager.
     * If the user's id is -1 it means that they do not exist in the system, and that they need to make an account
     * other exceptions are thrown in the userManager.
     * if the user ID is valid, the corresponding user is sent back through the objectOutputStream
     * @author Arcadia Youlten
     */

    private void loginUser(){

        try{
            //send back same int that I received, for the user to know that was is coming next is
            //the response to registering a user
            dataOutputStream.writeInt(Config.LOGIN_CLIENT_PROTOCOL);

            //the login
            String login = dataInputStream.readUTF();

            //the password
            String password = dataInputStream.readUTF();

            //attempt to login the user
            String userID = userManager.authenticateUser(login, password);

            //send back user object if successful
             this.user = userManager.getUserById(userID);
             objectOutputStream.writeObject(this.user);

        } catch (WrongPasswordException | UserNameNotFoundException | UserNotFoundException e ) {
            //send the exception back to the user to let them know what happened
            try{
                objectOutputStream.writeObject(e);
            } catch(IOException error_sending){
                error_sending.printStackTrace();
            }

        } catch(IOException e){
            //these exceptions are due to something going wrong with reading the object,
            //so we simply print the error for now
            e.printStackTrace();
        }

    }

}

