package view;

import controller.MenuController;
import controller.TopFiveController;
import model.Song;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.ArrayList;

/**This class's main purpose is to display and construct the view for the main menu
 * it offers methods to initialize the class and to add the content to the panels
 * <p>
 * @author Arcadia Youlten
 * @version %I% %G%
 */
public class MenuView{

    JButton jBManageSongs;
    JButton jBdisplayGraphs;
    JButton jBfiveBestSongs;

    /**
     * This method is used to initialize and set up the view so the top 5 songs view can be properly displayed.
     * Initialized the buttons and assigns text to them
     * @see MainView
     */

    public MenuView(){
        this.jBManageSongs = new JButton(MainView.MANAGE_SONGS);
        this.jBdisplayGraphs = new JButton(MainView.DISPLAY_GRAPHS);
        this.jBfiveBestSongs = new JButton(MainView.TOP_FIVE_SONGS);
    }

    /**
     * This method takes the necessary panels an adds them to the content pane.
     * A Box layout is used for structure, and vertical struts added for uniformity
     *
     * @return Returns the constructed panel to be placed in the main frame
     *
     * @see MainView
     * @see controller.MainController
     */

    public JPanel setupPanels() {

        JPanel jPmenuPanel = new JPanel();

        jBManageSongs.setAlignmentX(Box.CENTER_ALIGNMENT);
        jBManageSongs.setMinimumSize(new Dimension(MainView.MAIN_WINDOW_WIDTH, MainView.MAIN_WINDOW_HEIGHT / 3));
        jBManageSongs.setPreferredSize(new Dimension(MainView.MAIN_WINDOW_WIDTH, MainView.MAIN_WINDOW_HEIGHT / 3));
        jBManageSongs.setMaximumSize(new Dimension(MainView.MAIN_WINDOW_WIDTH, MainView.MAIN_WINDOW_HEIGHT / 3));

        jBdisplayGraphs.setAlignmentX(Box.CENTER_ALIGNMENT);
        jBdisplayGraphs.setMinimumSize(new Dimension(MainView.MAIN_WINDOW_WIDTH, MainView.MAIN_WINDOW_HEIGHT / 3));
        jBdisplayGraphs.setPreferredSize(new Dimension(MainView.MAIN_WINDOW_WIDTH, MainView.MAIN_WINDOW_HEIGHT / 3));
        jBdisplayGraphs.setMaximumSize(new Dimension(MainView.MAIN_WINDOW_WIDTH, MainView.MAIN_WINDOW_HEIGHT / 3));


        jBfiveBestSongs.setAlignmentX(Box.CENTER_ALIGNMENT);
        jBfiveBestSongs.setMinimumSize(new Dimension(MainView.MAIN_WINDOW_WIDTH, MainView.MAIN_WINDOW_HEIGHT / 3));
        jBfiveBestSongs.setPreferredSize(new Dimension(MainView.MAIN_WINDOW_WIDTH, MainView.MAIN_WINDOW_HEIGHT / 3));
        jBfiveBestSongs.setMaximumSize(new Dimension(MainView.MAIN_WINDOW_WIDTH, MainView.MAIN_WINDOW_HEIGHT / 3));


        jPmenuPanel.add(Box.createVerticalStrut(10));
        jPmenuPanel.add(jBManageSongs);
        jPmenuPanel.add(Box.createVerticalStrut(10));
        jPmenuPanel.add(jBdisplayGraphs);
        jPmenuPanel.add(Box.createVerticalStrut(10));
        jPmenuPanel.add(jBfiveBestSongs);
        jPmenuPanel.add(Box.createVerticalStrut(10));

        return jPmenuPanel;

    }

    /**
     * Method for registering the controller/action listener to each of the buttons in this view
     * @see MenuController for the actions performed with each button
     */

    public void registerButtonsMenu(ActionListener actionListener) {

        jBManageSongs.addActionListener(actionListener);
        jBManageSongs.setActionCommand(MainView.MANAGE_SONGS);

        jBdisplayGraphs.addActionListener(actionListener);
        jBdisplayGraphs.setActionCommand(MainView.DISPLAY_GRAPHS);

        jBfiveBestSongs.addActionListener(actionListener);
        jBfiveBestSongs.setActionCommand(MainView.TOP_FIVE_SONGS);

    }


}

