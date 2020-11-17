package view;

import model.BasicInfoSong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * this class is responsible for creating the view that lets the user choose
 * the song want to play. it offers the user the possibility to choose
 * between all public songs on the server, all their friend's uploads, and
 * all of their own songs.
 *
 * @author Amir Azzam and Sonia Leal
 * @version %I% %G%
 *
 * @see MainView
 * @see Controller.ChooseSongViewController
 */
public class ChooseSongView {
    public static final String BACK = "Back";
    public static final String PLAY = "Play";
    private final JButton back;
    private final JButton play;
    private JTable jTablePublicSongs;
    private JTable jTableFriendSongs;
    private JTable jTableOwnSongs;
    private JTabbedPane tabbedPane;

    /**
     * constructs an instance of this class and initializes the buttons,
     * add the images to them so they would be ready to be assigned an action
     * listener.
     */
    public ChooseSongView() {
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
     * this method creates the panel that contains the view of this class
     *
     * @param publicSongs all the public songs that are registered in the
     *                    system
     * @param friendsSongs all the public and private songs of all the
     *                     friends of the current user
     * @param ownSongs all the songs that are created by the current user
     * @return a panel with three tabs to choose from each containing one of
     * the lists above.
     */
    public JPanel songView(ArrayList<BasicInfoSong> publicSongs,
                           ArrayList<BasicInfoSong> friendsSongs,
                           ArrayList<BasicInfoSong> ownSongs){
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        mainPanel.setPreferredSize(new Dimension(500,400));

        // Column Names
        String[] columnNames = { "Song Name" , "Song ID"};

        String[][] ownSongsTable =
                new String[ownSongs.size()][2];
        for (int i = 0; i < ownSongs.size(); i++) {
            ownSongsTable[i][0] = ownSongs.get(i).getTitle();
            ownSongsTable[i][1] = String.valueOf(ownSongs.get(i).getId());
        }

        String[][] friendsSongsTable =
                new String[friendsSongs.size()][2];
        for (int i = 0; i < friendsSongs.size(); i++) {
            friendsSongsTable[i][0] = friendsSongs.get(i).getTitle();
            friendsSongsTable[i][1] = String.valueOf(friendsSongs.get(i).getId());
        }

        String[][] publicSongsTable =
                new String[publicSongs.size()][2];
        for (int i = 0; i < publicSongs.size(); i++) {
            publicSongsTable[i][0] = publicSongs.get(i).getTitle();
            publicSongsTable[i][1] = String.valueOf(publicSongs.get(i).getId());
        }

        jTableOwnSongs = new JTable(ownSongsTable, columnNames){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        jTableOwnSongs.getTableHeader().setReorderingAllowed(false);
        JScrollPane sp = new JScrollPane(jTableOwnSongs);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        jTableFriendSongs = new JTable(friendsSongsTable, columnNames){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        jTableFriendSongs.getTableHeader().setReorderingAllowed(false);
        JScrollPane sp2 = new JScrollPane(jTableFriendSongs);
        sp2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        jTablePublicSongs = new JTable(publicSongsTable, columnNames){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        jTablePublicSongs.getTableHeader().setReorderingAllowed(false);
        JScrollPane sp3 = new JScrollPane(jTablePublicSongs);
        sp3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        tabbedPane = new JTabbedPane();
        tabbedPane.add(sp, "Own Songs");
        tabbedPane.add(sp2, "Friend's Songs");
        tabbedPane.add(sp3, "Public Songs");

        mainPanel.add(tabbedPane);

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
    public void registerChooseSongListeners(ActionListener listener){
        play.setActionCommand(PLAY);
        back.setActionCommand(BACK);

        play.addActionListener(listener);
        back.addActionListener(listener);
    }

    public JTable getJTablePublicSongs() {
        return jTablePublicSongs;
    }

    public JTable getJTableFriendSongs() {
        return jTableFriendSongs;
    }

    public JTable getJTableOwnSongs() {
        return jTableOwnSongs;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
}
