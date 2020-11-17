package Controller;

import Server.CommunicationServer;
import view.MenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** * this is the controller to the {@link view.MenuView}
 *
 * @author Amir Azzam
 * @see view.MenuView
 * @see CommunicationServer
 * @see MainController
 * @see ActionListener
 */
public class MenuController extends MainController implements ActionListener {

    CommunicationServer communicationServer;

    /**
     * this constructor constructs a new controller, and links to the
     * communication server of the client
     * @param communicationServer the communication server of the client
     */
    public MenuController(CommunicationServer communicationServer) {
        this.communicationServer = communicationServer;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case MenuView.LOGOUT:
                logoutUser();
                moveToLogIn();
                break;
            case MenuView.PLAY_THE_PIANO:
                moveToPiano();
                break;
            case MenuView.PLAY_THE_SONG:
                getUserSongs();
                break;

            case MenuView.GET_FRIENDS:
                getUserFriends();
                break;
            case MenuView.MANAGE_ACCOUNT:
                moveToManageAccount(communicationServer.getUser());
                break;
        }
    }

    /**
     * this method logs the user out of the server
     */
    public void logoutUser(){
        communicationServer.logout();
    }

    public void getUserSongs(){
        communicationServer.sendGetSongsRequest();
    }

    public void getUserFriends(){
        communicationServer.requestUserFriends();
    }



}
