package view;

import Controller.PianoKeysController;
import Server.CommunicationServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/** This class creates a dialog to allow the user to save the song that has just been played. In this view it allows
 * the user to choose a name for the song, set the visibility and play the song. The user has the option to cancel
 * or save the song.
 *
 * @author Amir Azzam and Sonia Leal
 * @see PianoKeysController
 * @see CommunicationServer
 * @see ActionEvent
 * @see ActionListener
 */

public class SaveSongDialog{
    public static final String PLay = "play Before Submitting";
    private  JTextField field1;
    private JComboBox<String> field2;
    private final JButton play;

    public SaveSongDialog() throws HeadlessException {
        this.play = new JButton("play");
    }

    /**
     * this method shows a dialog so the user can create a song and save it
     * @return an integer indicating the option the user selected whether the
     * user selected okay (JOptionPane.OK_OPTION) or cancel (JOptionPane
     * .CANCEL_OPTION)
     *
     * @see JOptionPane
     */
    public int saveSongDialog(){
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(3,2));
        JLabel name = new JLabel("Song title: ");
        field1 = new JTextField(10);
        String[] fields = {"Private", "Public"};
        JLabel visibility = new JLabel("Song visibility: ");
        field2 = new JComboBox<>(fields);
        field2.setSelectedItem(0);
        JLabel playLabel = new JLabel("Play your Song: ");
        myPanel.add(name);
        myPanel.add(field1);
        myPanel.add(visibility);
        myPanel.add(field2);
        myPanel.add(playLabel);
        myPanel.add(play);

        return JOptionPane.showConfirmDialog(null,
                myPanel, "Save " + "Song", JOptionPane.OK_CANCEL_OPTION);
    }

    /**
     * used after the dialog is shown
     * @return the Title of the song the user entered
     */
    public String getSongTitle(){
        return field1.getText();
    }

    /**
     * used after the dialog is shown
     * @return the Visibility preference of the user
     */
    public boolean getVisibility(){
        return field2.getSelectedIndex() == 1;
    }

    /**
     * this method adds an action listener to all the buttons that are
     * created in this class
     * @param listener the action lister to be assigned
     */
    public void registerController(ActionListener listener){
        play.setActionCommand(PLay);
        play.addActionListener(listener);
    }
    public static void main(String[] args) {
        SaveSongDialog  saveSongDialog = new SaveSongDialog();
        if( saveSongDialog.saveSongDialog() != JOptionPane.CANCEL_OPTION)
            System.out.println(saveSongDialog.getSongTitle() +
                    " " +saveSongDialog.getVisibility() );
    }

}


