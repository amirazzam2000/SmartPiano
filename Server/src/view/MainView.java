package view;

import controller.MenuController;
import model.MusicCallback;
import model.Song;
import model.Statistics;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**This class' main purpose is to contain all of the information that we reuse across the different views
 * Acts as the "Manager" for all the views.
 *
 * <p>
 * @author Arcadia Youlten
 * @author Felipe Perez
 * @version %I% %G%
 */

public class MainView implements MusicCallback {

    private final TopFiveView topFiveView;
    private final GraphView graphView;
    private final ManageSongView manageSongView;
    private final MenuView menuView;
    private final JFrame frame;


    public static final String MANAGE_SONGS = "Manage Songs";
    public static final String DISPLAY_GRAPHS = "Plot Song Plays Evolution";
    public static final String TOP_FIVE_SONGS = "Top 5 Most Popular Songs";
    public static final String SERVER_MAIN_MENU = "Server Main Menu";
    public static final String BACK = "BACK";
    public static final String DELETE = "DELETE";
    public static final String EDIT = "EDIT";
    public static final int MAIN_WINDOW_HEIGHT = 250;
    public static final int MAIN_WINDOW_WIDTH = 300;
    public static final int TOP_SONG_WINDOW_HEIGHT = 300;
    public static final int TOP_SONG_WINDOW_WIDTH = 500;
    public static final int MANAGE_SONG_WINDOW_HEIGHT = 400;
    public static final int MANAGE_SONG_WINDOW_WIDTH = 500;
    public static final int GRAPH_WINDOW_HEIGHT = 600;
    public static final int GRAPH_WINDOW_WIDTH = 900;
    public static final int NUM_TOP_SONGS = 5;
    public static final int NUM_COLUMNS = 2;
    public static final int NUM_COLUMNS_MANAGE_SONG = 3;
    public static final String SONG_NAME = "Song Name";
    public static final String NUM_PLAYS = "Number of Plays";
    public static final String PUB = "Is Song Public?";
    public static final String SONG_ID = "Song Id";


    /**
     * Constructor method. Initializes the 3 different views the server contains, creates the Frame,
     * configures the frame and defaults to displaying the main menu.
     * @see MenuView
     * @see TopFiveView
     * @see ManageSongView
     * @see GraphView
     */
    public MainView(){

        topFiveView = new TopFiveView();
        graphView = new GraphView();
        manageSongView = new ManageSongView();

        this.frame = new JFrame();

        initialize();

        menuView = new MenuView();
        frame.add(menuView.setupPanels());
        frame.setVisible(true);
    }

    /**
     *  Method for initializing the main Frame that we use to place all the panels from the different view on.
     *  Sets up the content pane's size, title, and it's location in the center of the screen
     *  Sets the program to stop once we close the window.
     */

    private void initialize() {

        frame.getContentPane().removeAll();
        frame.setSize(MainView.MAIN_WINDOW_WIDTH, MainView.MAIN_WINDOW_HEIGHT + 80); //double the vertical margins added in setupPanels
        frame.setTitle(MainView.SERVER_MAIN_MENU);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Eliminate the previous panel contained within the main frame,
     * and place the panel returned from the TopFiveView class's setupPanels() method
     * @see TopFiveView
     * @see model.SongDAO
     * @param songs The top 5 songs.
     *              If the server has less than 5 songs, these will be displayed in order of plays, leaving
     *              some cells empty.
     */

    public void gotoTopFive(ArrayList<Song> songs){
        frame.getContentPane().removeAll();
        frame.setSize(TOP_SONG_WINDOW_WIDTH + 40, TOP_SONG_WINDOW_HEIGHT +40);
        frame.setTitle(TOP_FIVE_SONGS);
        frame.add(topFiveView.addPanels(songs));

        frame.repaint();
        frame.revalidate();
    }

    /**
     * Eliminate the previous panel contained within the main frame,
     * and place the panel returned from the MenuView class's setupPanels() method
     * @see MenuView
     */

    public void gotoMainMenu() {

        frame.getContentPane().removeAll();
        frame.setSize(MainView.MAIN_WINDOW_WIDTH, MainView.MAIN_WINDOW_HEIGHT + 80); //double the vertical margins added in setupPanels
        frame.setTitle(MainView.SERVER_MAIN_MENU);
        frame.add(menuView.setupPanels());

        frame.repaint();
        frame.revalidate();

    }

    /**
     * Eliminate the previous panel contained within the main frame,
     * and place the panel returned from the ManageSongView class's setupPanels() method
     * @see ManageSongView
     * @param allSongs All the songs contained in the server
     */

    public void gotoManageSongs(ArrayList<Song> allSongs) {

        frame.getContentPane().removeAll();
        frame.setSize(MainView.MANAGE_SONG_WINDOW_WIDTH, MainView.MANAGE_SONG_WINDOW_HEIGHT);
        frame.setTitle(MainView.MANAGE_SONGS);
        frame.add(manageSongView.addPanels(allSongs));

        frame.repaint();
        frame.revalidate();
    }

    /**
     * Eliminate the previous panel contained within the main frame,
     * and place the panel returned from the GraphView class's addPanels() method
     * @see GraphView
     * @param statistics Statistics stored on the server
     */

    public void gotoGraphs(ArrayList<Statistics> statistics) {

        frame.getContentPane().removeAll();
        frame.setSize(MainView.GRAPH_WINDOW_WIDTH, MainView.GRAPH_WINDOW_HEIGHT);
        frame.setTitle(MainView.DISPLAY_GRAPHS);

        frame.add(graphView.addPanel(statistics));
        frame.pack();
        frame.repaint();
        frame.revalidate();

    }

    /**
     * Method to show the user an error message if they try to edit a default song on the server
     * @see DialogBoxView
     * @see ManageSongView
     */

    public void gotoPopupException() {
        DialogBoxView.popup("The song you selected is a default song.\n You cannot edit or delete a default Song", "Default Song Selected");
    }

    /**
     * Method for registering the controller/action listener specific to the MenuView class
     * @see MenuView
     * @param actionListener
     */

    public void registerButtonsMenu(ActionListener actionListener){
        this.menuView.registerButtonsMenu(actionListener);
    }

    /**
     * Method for registering the controller/action listener specific to the MenuView class
     * @see TopFiveView
     * @param actionListener
     */

    public void registerButtonsTopFive(ActionListener actionListener){
        this.topFiveView.registerButtonsTopFive(actionListener);
    }

    /**
     * Method for registering the controller/action listener specific to the MenuView class
     * @see ManageSongView
     * @param actionListener
     */

    public void registerButtonsManageSong(ActionListener actionListener){
        this.manageSongView.registerButtonsManageSong(actionListener);
    }

    /**
     * Method for registering the controller/action listener specific to the MenuView class
     * @see GraphView
     * @param actionListener
     */

    public void registerButtonsGraph(ActionListener actionListener){
        this.graphView.registerButtonsGraph(actionListener);
    }

    /**
     * Method to update the songs in the ManageSongView if there is a change in the server
     * @see ManageSongView
     * @see MusicCallback
     * @param songs
     */
    @Override
    public void onMusicUpdated(ArrayList<Song> songs) {
        manageSongView.updatePanels(songs);
        frame.repaint();
        frame.revalidate();
    }



}
