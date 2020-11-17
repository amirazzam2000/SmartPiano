package Controller;

import Controller.LoginUserExceptions.EmptyCredentialsException;
import Server.CommunicationServer;
import Server.ServerCallbacks;
import model.BasicInfoSong;
import model.Song;
import view.LogInView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * the controller for the {@link view.LogInView} that allows to perform two actions: go
 * to the register view or login the user.
 *
 * @author Sonia Leal
 * @see view.LogInView
 */

public class LogInUserController extends MainController implements ActionListener, ServerCallbacks {

    private final CommunicationServer communicationServer;

    /**
     * Constructor for the login user controller
     * @param communicationServer Allows to establish communication with the server
     */
    public LogInUserController(CommunicationServer communicationServer) {
        this.communicationServer = communicationServer;
    }

    /**
     * Used to perform different actions depending on the event that has occurred. If the user clicked
     * the login button the the system will proceed to login the user. If the user clicked register they
     * will be directed to the registration view page.
     * @param e action that has happened
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case LogInView.LOGIN:
                logInUser(getLoginInput());
                break;

            case LogInView.REGISTER:
                moveToRegister();
                break;
        }

    }

    /**
     * Method used to login the user by verifying the credentials with the server.
     * @param inputs String that contains the credentials that the user has inputted.
     *              In the first position it has the nickname/email and in the second the password.
     */
    public void logInUser(String[] inputs){

        try {
            communicationServer.loginUser(inputs[0], inputs[1], this);
        } catch (EmptyCredentialsException e) {
            showErrorMessage(e.getMessage());
        }
    }

    @Override
    public void onUserReceived() {
        moveToMenu();
    }

    @Override
    public void onSongReceived(Song song) {

    }

    @Override
    public void onUserSongsReceived(ArrayList<BasicInfoSong> songs) {

    }
}
