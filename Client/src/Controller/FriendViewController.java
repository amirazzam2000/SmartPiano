package Controller;

import Server.CommunicationServer;
import Server.ServerCallbacks;
import model.BasicInfoSong;
import model.Song;
import view.ChooseSongView;
import view.FriendView;
import view.PianoSongsView;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * this is the controller to the {@link view.FriendView}
 *
 * it implements an action listener to respond to all the button clicks on
 * this views.
 *
 * @author Felipe perez
 *
 * @see view.FriendView
 * @see CommunicationServer
 * @see MainController
 * @see ActionListener
 */


public class FriendViewController extends MainController implements ActionListener, ListSelectionListener , ServerCallbacks {

    private final CommunicationServer communicationServer;
    private int selectedRow;
    private String friendCode;
    private String name;

    public FriendViewController(CommunicationServer communicationServer) {
        this.communicationServer = communicationServer;
        CommunicationServer.setFriendSongsCallback(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()){
            case FriendView.BACK:
                moveToMenu();
                break;

            case FriendView.ADD:
                if (!getAddFriendInput().equals(""))
                    communicationServer.requestAddFriendByCode(getAddFriendInput());
                else
                    MainController.showErrorMessage("You can leave this filed" +
                            " empty!");
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

        //once the user has let go of the mouse (no further events)
        if(!e.getValueIsAdjusting()){
            selectedRow = getJTableFriends().getSelectedRow();

            DefaultTableModel model = (DefaultTableModel) getJTableFriends().getModel();

            if(selectedRow != -1) {
                //go to the UserSongView with the user that has been selected
                friendCode = (String) model.getValueAt(selectedRow, 1);
                name = (String) model.getValueAt(selectedRow, 0);
                communicationServer.requestFriendSongsByCode((String) model.getValueAt(selectedRow, 1));
            }

        }
    }

    @Override
    public void onUserReceived() {

    }

    @Override
    public void onSongReceived(Song song) {

    }

    @Override
    public void onUserSongsReceived(ArrayList<BasicInfoSong> songs) {
        moveToUserSongs(songs, name,friendCode);
    }
}
