package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import controller.TopFiveController;
import model.*;

import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.util.ArrayList;

/**this class's main purpose is to display and construct the view for the top 5 songs played on the server
 * it offers methods to initialize the class and to add the content to the panels
 * <p>
 * @author Arcadia Youlten
 * @version %I% %G%
 *
 * @see Song
 */

public class TopFiveView{

    private final JButton jBBack;

    /**
     * This method is used to initialize and set up the view so the top 5 songs view can be properly displayed.
     * Initialized the back button and assigns an image to it
     */

    public TopFiveView() {
        Image image = ViewUtils.getScaledImageFromPath("Shared/src/view/assets/noun_back_1227057.bmp", null, 25, 25);
        this.jBBack = new JButton(new ImageIcon(image));
    }


    /**
     * This method takes the necessary panels an adds them to the content pane. In this case, we use a box layout so that
     * each one of the songs is displayed one after another in terms of number of plays.
     * a separator line and a space is added between each line to make it look more visually appealing
     * additionally, in the south we add a single back button to return to the previous menu
     * @param songs - an array of 5 songs to be added to the view. It is the top 5 songs that have been played on the system
     * @return Returns the constructed panel to be placed in the main frame
     *
     * @see MainView
     * @see controller.MainController
     */
    protected JPanel addPanels(ArrayList<Song> songs){

        JPanel jpNorth = new JPanel();
        jpNorth.setLayout(new BoxLayout(jpNorth,BoxLayout.PAGE_AXIS));

        // Column Names
        String[] columnNames = { MainView.SONG_NAME ,MainView.NUM_PLAYS};

        String[][] top_songs_and_plays = new String[MainView.NUM_TOP_SONGS][MainView.NUM_COLUMNS];
        for (int i = 0; i < songs.size(); i++) {
            top_songs_and_plays[i][0] = songs.get(i).getTitle();
            top_songs_and_plays[i][1] = String.valueOf(songs.get(i).getTimesPlayed());
        }

        // Initializing the JTable
        JTable jTable = new JTable(top_songs_and_plays, columnNames){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        jTable.getTableHeader().setReorderingAllowed(false);

        jTable.setBounds(30, 40, 200, 300);
        JScrollPane sp = new JScrollPane(jTable);
        jpNorth.add(sp);

        JPanel p =new JPanel();
        p.setLayout (new BoxLayout(p,BoxLayout.PAGE_AXIS));

        jpNorth.add(this.jBBack);
        p.setBorder(new EmptyBorder(10, 10, 0, 10));
        p.add(jpNorth);

        return jpNorth;

    }

    /**
     * Method for registering the controller/action listener to each of the buttons in this view
     * @see TopFiveController for the actions performed with each button
     */
    public void registerButtonsTopFive(ActionListener actionListener){

        jBBack.addActionListener(actionListener);
        jBBack.setActionCommand(MainView.BACK);

    }
}
