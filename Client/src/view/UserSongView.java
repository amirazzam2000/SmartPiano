package view;

import Controller.PianoKeysController;
import Server.CommunicationServer;
import model.BasicInfoSong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class is used to display the songs of a specific user's friend. This view displays a table that contains
 * a list of songs of a user's friend. The user can click on any song and they can play it by selecting a song and pressing
 * the Play button. The user also has the option to go back.
 *
 * @author Felipe Perez
 * @see PianoKeysController
 * @see CommunicationServer
 * @see ActionEvent
 * @see ActionListener
 * */
public class UserSongView {
    public static final String BACK_CUSTOM = "Back custom";
    public static final String PLAY_CUSTOM = "Play custom";
    private final JButton back;
    private final JButton play;
    private JTable jTableUserSongs;

    /**
     * This is is the constructor that sets up the back and play buttons.
     */
    public UserSongView() {
        Image image = ViewUtils.getScaledImageFromPath(ViewUtils.BACK, null, 25,
                25);
        back = new JButton(new ImageIcon(image));
        back.setBackground(Color.WHITE);

        image = ViewUtils.getScaledImageFromPath(ViewUtils.PLAY, null, 25,
                25);
        play = new JButton(new ImageIcon(image));
        play.setBackground(Color.WHITE);
    }

    /**
     * This method is used to set the view that shows the songs of a friend in a table format and allows the user
     * to select a specific song and play it. The user can also go back.
     * @param userSongs array that contains the songs of the user's friend
     * @param username username
     * @param userCode code of user
     * @return jPanel containing the view of the songs of and a few more functionalities (play and go back).
     */
    public JPanel songView(ArrayList<BasicInfoSong> userSongs,
                           String username, String userCode) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        mainPanel.setPreferredSize(new Dimension(500,400));


        // Column Names
        String[] columnNames = { "Song Name" , "Song ID"};

        String[][] ownSongsTable =
                new String[userSongs.size()][2];
        for (int i = 0; i < userSongs.size(); i++) {
            ownSongsTable[i][0] = userSongs.get(i).getTitle();
            ownSongsTable[i][1] = String.valueOf(userSongs.get(i).getId());
        }

        jTableUserSongs = new JTable(ownSongsTable, columnNames){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        jTableUserSongs.getTableHeader().setReorderingAllowed(false);
        JScrollPane sp = new JScrollPane(jTableUserSongs);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        mainPanel.add(sp);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jPanel.add(back);
        jPanel.add(play);

        mainPanel.add(jPanel);

        return mainPanel;
    }

    /**
     * this method adds an action listener to all the buttons that are
     * created in this class
     * @param listener the action lister to be assigned
     */
    public void registerUserSongsListeners(ActionListener listener){
        play.setActionCommand(PLAY_CUSTOM);
        back.setActionCommand(BACK_CUSTOM);

        play.addActionListener(listener);
        back.addActionListener(listener);
    }


    public JTable getjTableUserSongs() {
        return jTableUserSongs;
    }
}
