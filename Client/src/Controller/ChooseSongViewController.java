package Controller;

import Server.CommunicationServer;
import Server.ServerCallbacks;
import model.BasicInfoSong;
import model.Song;
import view.ChooseSongView;
import view.PianoSongsView;
import view.UserSongView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * this is the controller to the {@link view.ChooseSongView}, {@link view.ChooseSongView}
 * , and {@link view.UserSongView}
 *
 * it implements an action listener to respond to all the button clicks on
 * these views.
 *
 * @author Amir Azzam and Sonia Leal
 *
 * @see view.ChooseSongView
 * @see view.ChooseSongView
 * @see view.UserSongView
 * @see CommunicationServer
 * @see MainController
 * @see ActionListener
 */

public class ChooseSongViewController extends MainController implements ActionListener, ServerCallbacks , MusicCallbacks{

    private final CommunicationServer communicationServer;
    private final PianoKeysController pianoKeysController;

    public ChooseSongViewController(CommunicationServer communicationServer,
                                    PianoKeysController pianoKeysController) {
        this.communicationServer = communicationServer;
        this.pianoKeysController = pianoKeysController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()){
            case ChooseSongView
                    .BACK:

                moveToMenu();
                break;
            case ChooseSongView.PLAY:
                int songId = 0;
                switch (getTabbedPane().getSelectedIndex()){
                    case 0:
                        if(getJTableOwnSongs().getSelectedRow() != -1){
                           songId= Integer.parseInt(
                                   ((String) getJTableOwnSongs().getValueAt(getJTableOwnSongs().getSelectedRow(),1)));
                        }
                        break;
                    case 1:
                        if(getJTableFriendSongs().getSelectedRow() != -1) {
                            songId = Integer.parseInt(
                                    ((String) getJTableFriendSongs().getValueAt(getJTableFriendSongs().getSelectedRow(), 1)));
                        }
                        break;
                    case 2:
                        if(getJTablePublicSongs().getSelectedRow() != -1) {
                            songId = Integer.parseInt(
                                    ((String) getJTablePublicSongs().getValueAt(getJTablePublicSongs().getSelectedRow(), 1)));
                        }
                        break;
                }
                communicationServer.playSongById(songId,this);
                break;

            case PianoSongsView
                        .HIDE:
                PianoKeysController.stop();
                hidePopup();
            break;

            case PianoSongsView.MUTE:
                if(PianoKeysController.isMute()){
                    pianoKeysController.unMute();
                    changeToMute();
                }
                else{
                    pianoKeysController.mute();
                    changeToUnmute();
                }
                break;

            case UserSongView
                        .PLAY_CUSTOM:
                if(getJTableUserSongs().getSelectedRow() != -1) {
                    songId = Integer.parseInt(
                            ((String) getJTableUserSongs().getValueAt(getJTableUserSongs().getSelectedRow(), 1)));
                    communicationServer.playSongById(songId,this);
                }
                break;

            case UserSongView.BACK_CUSTOM:
                getUserFriends();
                break;

        }
    }

    public void getUserFriends(){
        communicationServer.requestUserFriends();
    }

    @Override
    public void onUserReceived() {

    }

    @Override
    public void onSongReceived(Song song) {
        moveToPianoSong();
        pianoKeysController.playSong(song.getFile(), this);
    }

    @Override
    public void onUserSongsReceived(ArrayList<BasicInfoSong> songs) {

    }

    @Override
    public void onSongDone() {
        hidePopup();
    }
}
