package view;

import controller.MainController;
import controller.ManageSongsController;
import controller.TopFiveController;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**This class's main purpose is to display and construct the view for managing the song view
 * it offers methods to initialize the class and to add the content to the panels
 * <p>
 * @author Arcadia Youlten
 * @version %I% %G%
 */

public class ManageSongView{

   private final JButton jBedit;
   private final JButton jBdelete;
   private final JButton jBBack;
   private DefaultTableModel jTableManageSongs;
    private JPanel jpSouth;

    /**
     * Initializes the buttons.
     * The back button is assigned an image, and edit and delete buttons are assigned text
     */

    public ManageSongView(){
        Image image = ViewUtils.getScaledImageFromPath("Shared/src/view/assets/noun_back_1227057.bmp", null, 25, 25);
        this.jBBack = new JButton(new ImageIcon(image));

        this.jBedit = new JButton("Edit Selected Song");
        this.jBdelete = new JButton("Delete Selected Song");
        jpSouth = new JPanel();
    }

    /**
     * This methods adds all of the content of the arraylist to a scroll panel so we can see all of the songs on the server
     * it also adds and edit button and delete button so information can be changed if we desire it
     * we also implement a back button to return to a previous menu
     *
     * @param songs The songs to be displayed. For this project, all the songs from the server are passed here.
     * @return Returns the constructed panel to be placed in the main frame
     */
    public JPanel addPanels(ArrayList<Song> songs){

        // Column Names
        String[] columnNames = { MainView.SONG_NAME ,MainView.PUB, MainView.SONG_ID};

        String[][] allSongs = new String[songs.size()][MainView.NUM_COLUMNS_MANAGE_SONG];
        for (int i = 0; i < songs.size(); i++) {
            allSongs[i][0] = songs.get(i).getTitle();
            allSongs[i][1] = String.valueOf(songs.get(i).isPublic());
            allSongs[i][2] = String.valueOf(songs.get(i).getId());
        }

        jTableManageSongs = new DefaultTableModel(allSongs, columnNames);
        JTable table = new JTable(jTableManageSongs){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        table.getTableHeader().setReorderingAllowed(false);
        MainController.informationToController(table);
        JScrollPane sp = new JScrollPane(table);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jPanel.add(jBBack);
        jPanel.add(jBedit);
        jPanel.add(jBdelete);

        jpSouth = new JPanel();
        jpSouth.setLayout(new BoxLayout(jpSouth,BoxLayout.PAGE_AXIS));

        jpSouth.add(sp);
        jpSouth.add(jPanel);

        return jpSouth;

    }

    /**
     * Method for registering the controller/action listener to each of the buttons in this view
     * @see ManageSongsController for the actions performed with each button
     */
    public void registerButtonsManageSong(ActionListener actionListener){

        jBedit.addActionListener(actionListener);
        jBedit.setActionCommand(MainView.EDIT);

        jBdelete.addActionListener(actionListener);
        jBdelete.setActionCommand(MainView.DELETE);

        jBBack.addActionListener(actionListener);
        jBBack.setActionCommand(MainView.BACK);
    }

    /**
     * Method for updating the song information in real time as it is modified.
     * If a user adds a song, the server will update the info in the view in real time.
     *
     * @param songs The songs to be displayed. For this project, all the songs from the server are passed here.
     *
     * @see MusicCallback
     */
    public void updatePanels(ArrayList<Song> songs) {
        // Column Names
        String[] columnNames = { MainView.SONG_NAME ,MainView.PUB, MainView.SONG_ID};

        String[][] allSongs = new String[songs.size()][MainView.NUM_COLUMNS_MANAGE_SONG];
        for (int i = 0; i < songs.size(); i++) {

            allSongs[i][0] = songs.get(i).getTitle();
            allSongs[i][1] = String.valueOf(songs.get(i).isPublic());
            allSongs[i][2] = String.valueOf(songs.get(i).getId());

        }

        jTableManageSongs = new DefaultTableModel(allSongs, columnNames);
        JTable table = new JTable(jTableManageSongs);
        MainController.informationToController(table);
        JScrollPane sp = new JScrollPane(table);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jPanel.add(jBBack);
        jPanel.add(jBedit);
        jPanel.add(jBdelete);

        jpSouth.removeAll();
        jpSouth.setLayout(new BoxLayout(jpSouth,BoxLayout.PAGE_AXIS));

        jpSouth.add(sp);
        jpSouth.add(jPanel);
    }
}
