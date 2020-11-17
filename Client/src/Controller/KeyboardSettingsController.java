package Controller;

import Server.CommunicationServer;
import view.KeyboardSettingsView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** The aim of this class is to perform the desired actions when the user pressed the
 * Save or Back buttons. This is the controller for the {@link view.KeyboardSettingsView}
 *
 * @author Sonia Leal
 * @see KeyboardSettingsView
 * @see CommunicationServer
 * @see ActionEvent
 * @see ActionListener
 */

public class KeyboardSettingsController extends MainController implements ActionListener {

    /**
     * Method to perform an action depending on which event was triggered.
     * @param e which allows to know if the user pressed the back of save button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case KeyboardSettingsView.BACK:
                moveToPiano();
                break;
            case KeyboardSettingsView.SAVE:
                setTableValues();
                moveToPiano();
                break;
        }
    }


    /**
     * This method is called once the user has pressed the save button. The objective of this method is
     * to update the keyboard keys according the the user's inputs. For example, if the user has chosen
     * to play "C#" with the key "Q", then it will be updated.
     */
    private void setTableValues(){

        for (int i = 0; i < getKeyboardSettingsTable().getRowCount(); i++) {
            PianoKeysController.gethMap().put((String) getKeyboardSettingsTable().getValueAt(i, 0),
                    ((String) getKeyboardSettingsTable().getValueAt(i, 1)).toUpperCase().charAt(0));
        }
    }
}
