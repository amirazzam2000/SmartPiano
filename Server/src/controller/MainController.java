package controller;

import model.SongDAO;

import model.StatisticsExceptions.StatisticsEmptyException;
import view.MainView;
import view.ManageSongView;

import javax.swing.*;

/**
 * Controller class for the Server. Manages all the controllers (Inheritance)
 * We instantiate the important class fields with a constructor provided.
 * @see MainView
 * @see SongDAO
 * <p>
 * @author Felipe Perez
 * @version %I% %G%
 */

public class MainController {
    private  static MainView view;
    private  static SongDAO songDAO;
    private  static JTable jTableManageSongs;

    public MainController(){}

    /**
     * Constructor class
     * @param songDAO   We call all song related functionality methods with this object
     * @param view      We call all view related methods with this object
     */

    public MainController( SongDAO songDAO, MainView view) {
        MainController.songDAO = songDAO;
        MainController.view = view;
        SongDAO.setCallback(view);
    }

    /**
     * Method for the MainController that RETURNS the JTable attribute in the class
     * @see ManageSongView for the actions performed with each button
     * @return jTableManageSongs
     */

    protected JTable getjTableManageSongs() {
        return jTableManageSongs;
    }

    /**
     * Method for the MainController that RETURNS the songDAO attribute in the class
     * @return songDAO
     */

    protected SongDAO getSongDAO() {
        return songDAO;
    }

    /**
     * Method that calls the corresponding goto method in the main view
     * @see MainView
     */

    protected void gotoMainMenu(){
        view.gotoMainMenu();
    }

    /**
     * Method that calls the corresponding goto method in the main view
     * @see MainView
     */

    protected void gotoManageSongs(){
        view.gotoManageSongs(songDAO.getAllSongs());
    }

    /**
     * Method that calls the corresponding goto method in the main view
     * @see MainView
     */

    protected void gotoTopFive(){
        view.gotoTopFive(songDAO.getTopSongs());
    }

    /**
     * Method that calls the corresponding goto method in the main view
     * @see MainView
     */

    protected void gotoPopupException(){view.gotoPopupException();}

    /**
     * Method that calls the corresponding goto method in the main view
     * @see MainView
     */

    protected void gotoGraphs(){
        try {
            view.gotoGraphs(songDAO.getStatistics());
        }catch (StatisticsEmptyException e){
            JOptionPane option = new JOptionPane();
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Warning", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Method for registering the controller/action listener to each of the buttons in this view
     * @see MenuController for the actions performed with each button
     */

    public void assignListeners(GraphController graphController,
                                MenuController menuController,
                                ManageSongsController manageSongsController,
                                TopFiveController topFiveController){

        view.registerButtonsManageSong(manageSongsController);
        view.registerButtonsMenu(menuController);
        view.registerButtonsTopFive(topFiveController);
        view.registerButtonsGraph(graphController);
    }

    /**
     * Method for the MainController to ASSIGN the JTable contained in the ManageSongView class
     * @see ManageSongView for the actions performed with each button
     */

    public static void informationToController(JTable jTable){
       jTableManageSongs = jTable;
    }

}
