package Controller;

import Server.CommunicationServer;
import Server.ServerCallbacks;
import model.BasicInfoSong;
import model.Song;
import model.User;
import view.KeyboardSettingsView;
import view.MainView;

import javax.swing.*;
import java.util.ArrayList;

/**
 * This class is in charge of handling the different views for the client side. Some of the views that this
 * class offers is the login view and the register view. Moreover, this class in also used to assign listeners and
 * and get the inputs for login and register.
 *
 * @author Amir Azzam, Sonia leal, and Felipe perez
 */
public class MainController {
    private static MainView view;

    public MainController() {

    }

    /**
     * Constructor that contains the main view
     * @param view the main view
     */
    public MainController(MainView view){
        MainController.view = view;
        CommunicationServer.setFriendsCallback(view);
    }

    /**
     * Method to go to the login view
     */
    public void moveToLogIn(){
        view.moveToLogin();
    }

    /**
     * Method to go to the register view
     */
    protected void moveToRegister(){
        view.moveToRegister();
    }

    /**
     * Method to go to the Menu view
     */
    protected void moveToMenu(){
        view.moveToMenu();
    }

    protected void moveToPianoSong(){
        view.moveToPianoSong();
    }

    /**
     * Method to go to the Menu view
     */
    protected void moveToPiano(){
        view.moveToPiano();
    }
    protected JTable getJTablePublicSongs() {
        return view.getJTablePublicSongs();
    }

    protected void moveToManageAccount(User user){
        view.moveToManageAccount(user);
    }

    protected void moveToKeyboardSettings(){ view.moveToKeyboardSettings();}

    protected void hidePopup(){
        view.hidePopup();
    }

    public void changeToMute(){
        view.changeToMute();
    }

    public void changeToUnmute(){
        view.changeToUnmute();
    }

    protected JTable getJTableFriendSongs() {
        return view.getJTableFriendSongs();
    }

    protected JTable getJTableOwnSongs() {
        return view.getJTableOwnSongs();
    }

    protected JTable getJTableFriends() {
        return view.getJTableFriends();
    }

    protected JTabbedPane getTabbedPane() {
        return view.getTabbedPane();
    }

    protected JTable getJTableUserSongs() {
        return view.getJTableUserSongs();
    }
    protected JTable getKeyboardSettingsTable(){ return view.getKeyboardSettingsTable();}


    /**
     *
     * Method to get the parameters of the login (username/email and password)
     * @return the parameters in a String array for the login which are: username/email and password
     */
    protected String[] getLoginInput(){
        return view.getLoginInput();
    }

    /**
     * Method to get the parameters for the registration (username, email and password)
     * @return the parameters in a String array for the registration which are: username, email and password
     */
    protected String[] getRegisterInput(){
        return view.getRegisterInput();
    }


    /**
     *
     * Method to get the string of the friend that needs to be added (user code)
     * @return The string that was input into the text field
     */
    protected String getAddFriendInput(){
        return view.getAddFriendInput();
    }

    /**
     * changes the button in the piano ui to display the pause icon instead
     * of the record icon
     */
    protected void changeRecordButtonToPause(){
        view.changeRecordButtonToPause();
    }

    /**
     * changes the button in the piano ui to display the record icon instead
     * of the pause icon
     */
    protected void changeRecordButtonToRecord(){
        view.changeRecordButtonToRecord();
    }

    /**
     * shows a pop dialog with the specified method
     * @param message the message to be shown on the dialog
     */
    public static void showErrorMessage(String message){
        view.showErrorDialog(message);
    }

    /**
     * this method shows a dialog so the user can create there song and save
     * it
     * @return an integer indecating the option the user selected whether the
     * user selected okay (JOptionPane.OK_OPTION) or cancel (JOptionPane
     * .CANCEL_OPTION)
     *
     * @see JOptionPane
     */
    protected int showSaveSongDialog(){
        return view.showSaveSongDialog();
    }

    /**
     * used after the dialog is shown
     * @return the Title of the song the user entered
     */
    protected String getSongTitle(){
        return view.getSongTitle();
    }

    /**
     * used after the dialog is shown
     * @return the Visibility preference of the user
     */
    protected boolean getVisibility(){
        return view.getVisibility();
    }

    protected JPanel getPianoPanel(){
        return view.getPianoPanel();
    }

    protected void changeKeyColor(String keyId){
        view.changeKeyColor(keyId);
    }

    protected void resetKeyColor(String keyId){
        view.resetKeyColor(keyId);
    }

    public static void receiveSongs(ArrayList<BasicInfoSong> publicSongs,
                             ArrayList<BasicInfoSong> friendsSongs,
                             ArrayList<BasicInfoSong> ownSongs){
        view.moveToSongView(publicSongs,friendsSongs,ownSongs);
    }

    public static void moveToUserSongs(ArrayList<BasicInfoSong> userSongs,
                                       String username, String userCode){
        view.moveToUserSongView(userSongs, username, userCode);
    }

    public static void receiveFriends(ArrayList<User> friends){
        view.moveToFriendView(friends);
    }

    /**
     * This method is used to assign listeners for the login and registration
     * @param logInUserController this is the controller to login the user
     * @param registerUserController this is the controller to register the user
     * @param pianoKeysController
     * @param manageAccountController
     */
    public void assignListeners(LogInUserController logInUserController,
                                RegisterUserController registerUserController,
                                MenuController menuController,
                                PianoKeysController pianoKeysController,
                                ChooseSongViewController chooseSongViewController,
                                FriendViewController friendViewController,
                                ManageAccountController manageAccountController,
                                KeyboardSettingsController keyboardSettingsController) {
        view.registerLoginListeners(logInUserController);
        view.registerRegisterListeners(registerUserController);
        view.registerMenuListeners(menuController);
        view.registerPianoListeners(pianoKeysController);
        view.registerSaveSongListeners(pianoKeysController);
        view.registerChooseSongListeners(chooseSongViewController);
        view.registerFriendViewListeners(friendViewController);
        view.registerManageSongListeners(manageAccountController);
        view.registerKeyboardSettingsListeners(keyboardSettingsController);
    }


}
