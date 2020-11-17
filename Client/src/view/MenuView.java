package view;

import Controller.PianoKeysController;
import Server.CommunicationServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is a class to create the Menu view.
 *
 * @author Amir Azzam
 * @see Controller.MenuController
 * @see CommunicationServer
 * @see ActionEvent
 * @see ActionListener
 */
public class MenuView {
    public static final String LOGOUT = "Logout";
    public static final String PLAY_THE_PIANO = "Play The Piano";
    public static final String PLAY_THE_SONG = "Play A Song";
    public static final String GET_FRIENDS = "Friends";
    public static final String MANAGE_ACCOUNT = "Manage Account";


    private JPanel panel;
    private final JButton logout;
    private final JButton piano;
    private final JButton playSong;
    private final JButton getFriend;
    private final JButton manageAccount;

    /**
     * Constructor method that includes two JButtons: register and login
     */
    public MenuView(){

        logout = new JButton(LOGOUT);
        piano = new JButton(PLAY_THE_PIANO);
        playSong = new JButton(PLAY_THE_SONG);
        getFriend = new JButton(GET_FRIENDS);
        manageAccount = new JButton(MANAGE_ACCOUNT);

    }

    /**
     * This method is used to create a JPanel to illustrate the log in view. This view has two buttons:
     * register and login. Also, it has text fields so the user can enter their credentials.
     * @return a JPanel with the log in view
     */
    public JPanel MenuView(){

        panel = new JPanel(new GridLayout(5,1));

        panel.add(piano);
        panel.add(playSong);
        panel.add(getFriend);
        panel.add(manageAccount);
        panel.add(logout);

        return panel;
    }

    /**
     * Method to set and add the action listeners for the login and register actions.
     * @param listener indicates the action listener to be added
     */
    public void registerActionListener(ActionListener listener){
        logout.setActionCommand(LOGOUT);
        logout.addActionListener(listener);

        piano.setActionCommand(PLAY_THE_PIANO);
        piano.addActionListener(listener);

        playSong.setActionCommand(PLAY_THE_SONG);
        playSong.addActionListener(listener);

        getFriend.setActionCommand(GET_FRIENDS);
        getFriend.addActionListener(listener);

        manageAccount.setActionCommand(MANAGE_ACCOUNT);
        manageAccount.addActionListener(listener);
    }

}
