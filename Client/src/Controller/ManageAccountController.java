package Controller;

import Server.CommunicationServer;
import view.ManageAccountView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * this is the controller to the {@link view.ManageAccountView}
 *
 * it implements an action listener to respond to all the button clicks on
 * this views.
 *
 * @author Amir Azzam
 *
 * @see view.ManageAccountView
 * @see CommunicationServer
 * @see MainController
 * @see ActionListener
 */
public class ManageAccountController extends MainController implements ActionListener, UserCallback {
    private final CommunicationServer communicationServer;

    public ManageAccountController(CommunicationServer cs) {
        communicationServer = cs;
        CommunicationServer.setUserCallback(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case ManageAccountView
                    .BACK:
                moveToMenu();
                break;
            case ManageAccountView.DELETE_ACCOUNT:
                communicationServer.deleteCurrentUser();
                break;
        }
    }

    @Override
    public void onUserDeleted() {
        moveToLogIn();
    }
}
