package Controller;

import Server.CommunicationServer;
import Server.ServerCallbacks;
import model.BasicInfoSong;
import model.Song;
import view.RegisterUserView;
import model.User;
import model.UserExceptions.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * this is the controller to the {@link view.RegisterUserView}
 *
 * @author Amir Azzam
 * @see view.RegisterUserView
 * @see CommunicationServer
 * @see MainController
 * @see ActionListener
 */
public class RegisterUserController extends MainController implements ActionListener, ServerCallbacks {

    CommunicationServer communicationServer;

    /**
     * this constructor constructs a new controller, and links to the
     * communication server of the client
     * @param communicationServer the communication server of the client
     */
    public RegisterUserController(CommunicationServer communicationServer) {
        this.communicationServer = communicationServer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case RegisterUserView.BACK:
                moveToLogIn();
                break;
            case RegisterUserView.REGISTER:
                registerUser(getRegisterInput());
                break;
        }
    }

    /**
     * this method send an new user through the communication server to the
     * Server, in order to register them
     * @param inputs the inputs from the {@link RegisterUserView}
     */
    public void registerUser(String[] inputs){
        if(inputs[2].equals(inputs[3])){
            User user = new User(inputs[0], inputs[1],inputs[2]);
            try {

                communicationServer.registerUser(user, this);

            } catch (WrongFormatException | EmptyFieldException |
                    PasswordSpecialException | PasswordUpperException |
                    PasswordDigitException | PasswordLowerException |
                    PasswordShortException e) {
                showErrorMessage(e.getMessage());
            }
        }else{
            showErrorMessage("Passwords don't match");
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
