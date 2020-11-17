package view;

import Controller.FriendsCallback;
import Controller.KeyboardSettingsController;
import Controller.PianoKeysController;
import model.BasicInfoSong;
import model.User;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * this class is the main view of the client, it contains the main JFrame of
 * the client.
 *
 * @author Amir Azzam
 * @version %I% %G%
 *
 * @see LogInView
 * @see RegisterUserView
 * @see JFrame
 */
public class MainView implements FriendsCallback {
    private LogInView logInView;
    private final RegisterUserView registerUserView;
    private final MenuView menuView;
    private final PianoKeys piano;
    private final PlayPianoView playPianoView;
    private final PianoSongsView pianoSongsView;
    private final ChooseSongView chooseSongView;
    private final FriendView friendView;
    private final ManageAccountView manageAccountView;
    private final UserSongView userSongView;
    private final KeyboardSettingsView keyboardSettingsView;
    private final SaveSongDialog saveSongDialog;
    private JPanel auxPanel;
    private final JFrame frame;
    private JFrame f;

    /**
     * this is method constructs a MainView by initializing all the
     * attributes and creating the Main frame.
     * <p>
     * this method sets the view to the {@link LogInView} by default
     */

    public MainView(){
        logInView = new LogInView();
        registerUserView = new RegisterUserView();
        menuView = new MenuView();
        chooseSongView = new ChooseSongView();
        friendView = new FriendView();
        piano = new PianoKeys();
        pianoSongsView = new PianoSongsView();
        saveSongDialog = new SaveSongDialog();
        playPianoView = new PlayPianoView();
        manageAccountView = new ManageAccountView();
        keyboardSettingsView = new KeyboardSettingsView();
        userSongView = new UserSongView();
        frame = new JFrame();
        frame.getContentPane().removeAll();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,150);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        logInView = new LogInView();

    }

    /**
     * this method changes the view in the main frame to the {@link LogInView}
     */
    public  void moveToLogin(){
        frame.getContentPane().removeAll();
        frame.setSize(400,150);
        frame.add(logInView.LogInView());
        frame.setTitle("Login");
        frame.repaint();
        frame.revalidate();
        frame.setVisible(true);
    }

    /**
     * this method changes the view in the main frame to the {@link RegisterUserView}
     */
    public void moveToRegister(){
        frame.getContentPane().removeAll();
        frame.setSize(400,200);
        frame.add(registerUserView.registerUserView());
        frame.setTitle("Sign Up");
        frame.repaint();
        frame.revalidate();
        frame.setVisible(true);
    }

    /**
     * this method changes the view in the main frame to the {@link RegisterUserView}
     */
    public  void moveToMenu(){
        frame.getContentPane().removeAll();
        frame.setSize(400,200);
        frame.add(menuView.MenuView());
        frame.setTitle("Menu");
        frame.repaint();
        frame.revalidate();
        frame.setVisible(true);
    }

    /**
     * this method changes the view in the main frame to the {@link PlayPianoView}
     */
    public void moveToPiano(){
        frame.getContentPane().removeAll();
        auxPanel = playPianoView.pianoKeys();
        frame.add(auxPanel);
        frame.setTitle("piano");
        frame.pack();
        frame.repaint();
        frame.revalidate();
        frame.setVisible(true);
    }

    /**
     * this method changes the view in the main frame to the {@link PianoSongsView}
     */
    public void moveToPianoSong(){
        /*PopupFactory factory = PopupFactory.getSharedInstance();
        popup = factory.getPopup(frame,
                pianoSongsView.pianoKeys(getPianoKey()),300,
                300);
        popup.show();*/
        f = new JFrame();
        f.setLocationRelativeTo(null);
        f.getContentPane().removeAll();
        f.add(pianoSongsView.pianoKeys(getPianoKey()));
        f.setTitle("piano");
        f.pack();
        f.repaint();
        f.revalidate();
        f.setVisible(true);
    }

    /**
     * hides the popup
     */
    public void hidePopup(){
        f.dispose();
    }

    /**
     * this method changes the view in the main frame to the {@link ChooseSongView}
     */
    public void moveToSongView(ArrayList<BasicInfoSong> publicSongs,
                               ArrayList<BasicInfoSong> friendsSongs,
                               ArrayList<BasicInfoSong> ownSongs){
        frame.getContentPane().removeAll();
        frame.add(chooseSongView.songView(publicSongs, friendsSongs, ownSongs));
        frame.setTitle("piano");
        frame.pack();
        frame.repaint();
        frame.revalidate();
        frame.setVisible(true);
    }

    /**
     * this method changes the view in the main frame to the {@link UserSongView}
     */
    public void moveToUserSongView(ArrayList<BasicInfoSong> userSongs,
                                   String username, String userCode) {
        frame.getContentPane().removeAll();
        frame.add(userSongView.songView(userSongs,username,userCode));
        frame.setTitle("Friend's Songs");
        frame.pack();
        frame.repaint();
        frame.revalidate();
        frame.setVisible(true);
    }

    /**
     * this method changes the view in the main frame to the {@link FriendView}
     */
    public void moveToFriendView(ArrayList<User> friends) {
        frame.getContentPane().removeAll();
        frame.add(friendView.friendView(friends));
        frame.setTitle("Friends");
        frame.pack();
        frame.repaint();
        frame.revalidate();
        frame.setVisible(true);
    }
    /**
     * this method changes the view in the main frame to the {@link ManageAccountView}
     */
    public void moveToManageAccount(User user){
        frame.getContentPane().removeAll();
        frame.setSize(400,200);
        frame.add(manageAccountView.manageAccount(user));
        frame.setTitle("Manage Account");
        frame.pack();
        frame.repaint();
        frame.revalidate();
        frame.setVisible(true);
    }

    /**
     * this method changes the view in the main frame to the {@link KeyboardSettingsView}
     */
    public void moveToKeyboardSettings(){
        frame.getContentPane().removeAll();
        frame.setSize(300, 350);
        frame.add(keyboardSettingsView.settingsPanel());
        frame.setTitle("Keyboard settings");
        frame.pack();
        frame.repaint();
        frame.revalidate();
        frame.setVisible(true);
    }

    /**
     * this method changes the color of the keys
     */
    public void changeKeyColor(String keyID){
        piano.changeKeyColor(keyID);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * this method resets the color of the keys
     */
    public void resetKeyColor(String keyID){
        piano.resetKeyColor(keyID);
        frame.revalidate();
        frame.repaint();
    }

    public static JPanel getPianoKey (){
        return PianoKeys.getPanel();
    }

    public JTable getJTablePublicSongs() {
        return chooseSongView.getJTablePublicSongs();
    }

    public JTable getJTableFriendSongs() {
        return chooseSongView.getJTableFriendSongs();
    }

    public JTable getJTableOwnSongs() {
        return chooseSongView.getJTableOwnSongs();
    }

    public JTable getJTableUserSongs() {
        return userSongView.getjTableUserSongs();
    }

    public JTable getJTableFriends() {
        return friendView.getJTableFriends();
    }

    public JTabbedPane getTabbedPane() {
        return chooseSongView.getTabbedPane();
    }

    public JTable getKeyboardSettingsTable() { return keyboardSettingsView.getTable();}

    /**
     * shows a pop dialog with the specified method
     * @param message the message to be shown on the dialog
     */
    public void showErrorDialog(String message){
        JOptionPane option = new JOptionPane();
        JOptionPane.showMessageDialog(null, message,
                "Warning", JOptionPane.ERROR_MESSAGE);
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
    public int showSaveSongDialog(){
        return saveSongDialog.saveSongDialog();
    }

    /**
     * used after the dialog is shown
     * @return the Title of the song the user entered
     */
    public String getSongTitle(){
        return saveSongDialog.getSongTitle();
    }

    /**
     * used after the dialog is shown
     * @return the Visibility preference of the user
     */
    public boolean getVisibility(){
        return saveSongDialog.getVisibility();
    }

    /**
     * obtains the piano panel
     * @return the piano panel
     */
    public JPanel getPianoPanel(){ return piano.getPanel(); }

    /**
     * registers the action listener of the {@link SaveSongDialog}
     * @param listener the action listener to be registered
     */
    public void registerSaveSongListeners(ActionListener listener){
        saveSongDialog.registerController(listener);
    }

    /**
     * registers the action listener of the {@link LogInView}
     * @param listener the action listener to be registered
     */
    public void registerLoginListeners(ActionListener listener){
        logInView.registerActionListener(listener);
    }


    /**
     * registers the action listener of the {@link RegisterUserView}
     * @param listener the action listener to be registered
     */
    public void registerRegisterListeners(ActionListener listener){
        registerUserView.registerActionListener(listener);
    }

    /**
     * registers the action listener of the {@link RegisterUserView}
     * @param listener the action listener to be registered
     */
    public void registerMenuListeners(ActionListener listener){
        menuView.registerActionListener(listener);
    }

    /**
     * registers the action listener of the {@link ChooseSongView}, {@link PianoSongsView}, and {@link UserSongView}
     * @param listener the action listener to be registered
     */
    public void registerChooseSongListeners(ActionListener listener){
        chooseSongView.registerChooseSongListeners(listener);
        pianoSongsView.registerController(listener);
        userSongView.registerUserSongsListeners(listener);

    }

    /**
     * registers the action listener of the {@link FriendView}
     * @param listener the action listener to be registered
     */
    public void registerFriendViewListeners(ActionListener listener){
        friendView.registerFriendViewListeners(listener);
    }

    /**
     * registers the action listener of the {@link PianoKeys}
     * @param listener the action listener to be registered
     */
    public void registerPianoListeners(PianoKeysController listener) {
        piano.registerController(listener);
        playPianoView.registerController(listener);
    }

    /**
     * registers the action listener of the {@link ManageAccountView}
     * @param listener the action listener to be registered
     */
    public void registerManageSongListeners(ActionListener listener){
        manageAccountView.registerManageSongListeners(listener);
    }

    /**
     * registers the action listener of the {@link KeyboardSettingsView}
     * @param listener the action listener to be registered
     */
    public void registerKeyboardSettingsListeners(ActionListener listener){
        keyboardSettingsView.registerKeyboardSettingsListeners(listener);
    }

    /**
     * get's the information entered by the user in the text fields on the
     * screen in the {@link LogInView}
     * @return a string the has two fields:
     * <p>the first is the login/username
     * <p>the second one is the password
     */
    public String[] getLoginInput(){
        return logInView.getInput();
    }

    /**
     * get's the information entered by the user in the text field on the
     * screen in the {@link FriendView}
     * @return a string with the user input
     */
    public String getAddFriendInput(){
        return friendView.getInput();
    }

    /**
     * get's the information entered by the user in the text fields on the
     * screen in the {@link RegisterUserView}
     * @return a string with three fields:
     * <p>the first is the username,
     * <p>the second is the email,
     * <p>the third is the password
     */
    public String[] getRegisterInput() {
        return registerUserView.getInput();
    }

    /**
     * changes the button in the piano ui to display the pause icon instead
     * of the record icon
     */
    public void changeRecordButtonToPause(){
        playPianoView.pause();
    }

    /**
     * changes the button in the piano ui to display the record icon instead
     * of the pause icon
     */
    public void changeRecordButtonToRecord(){
        playPianoView.record();
    }

    /**
     * Changes the icon to mute in the {@link PianoSongsView}
     */
    public void changeToMute(){
        pianoSongsView.mute();
        f.repaint();
        f.revalidate();
    }

    /**
     * Changes the icon to unmute in the {@link PianoSongsView}
     */
    public void changeToUnmute(){
        pianoSongsView.unMute();
        f.repaint();
        f.revalidate();
    }

    @Override
    public void onFriendsUpdated(ArrayList<User> friends) {
        friendView.updateFriends(friends);
        frame.repaint();
        frame.revalidate();
    }
}
