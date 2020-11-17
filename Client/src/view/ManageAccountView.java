package view;

import Controller.PianoKeysController;
import Server.CommunicationServer;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This is class creates the view for the account management. This class contains two buttons:back and delete account.
 * Moreover, this class create a JPanel that contains the information of the user (username, friendship code,
 * and email).
 *
 * @author Amir Azzam
 * @see Controller.ManageAccountController
 * @see CommunicationServer
 * @see ActionEvent
 * @see ActionListener
 */
public class ManageAccountView {
    public static final String BACK = "Back";
    public static final String DELETE_ACCOUNT = "Delete Account";

    private final JButton back;
    private final JButton deleteAccount;

    /**
     *Constructor for the class which creates and sets up the buttons.
     */
    public ManageAccountView() {
        Image image = ViewUtils.getScaledImageFromPath(ViewUtils.BACK, null, 25,
                25);
        back = new JButton(new ImageIcon(image));
        back.setBackground(Color.WHITE);

        deleteAccount = new JButton(DELETE_ACCOUNT);
        deleteAccount.setBackground(Color.WHITE);
        deleteAccount.setPreferredSize(new Dimension(150,35));

    }

    /**
     * This method returns a JPanel that holds a table with the user's information (username, friendship code, and
     * email). It also contains a button to delete the account and another to go back.
     * @param user user that is currently interacting with the system
     * @return JPanel with a table that has the user's information and two buttons (delete and back).
     */
    public JPanel manageAccount(User user){
        JPanel mainPanel = new JPanel();
        mainPanel.setSize(400,200);
        mainPanel.setLayout(new BorderLayout());

        String[][] values = new String[3][2];
        values[0][0] = "Username : ";
        values[0][1] =  user.getNickname();

        values[1][0] =  "Friendship Code : ";
        values[1][1] = user.getCode();

        values[2][0] = "Email : ";
        values[2][1] = user.getEmail();

        String[] columns = {"",""};
        JTable valuesTable = new JTable(values,columns){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        valuesTable.getTableHeader().setReorderingAllowed(false);
        valuesTable.setPreferredSize(new Dimension(400,150));

        mainPanel.add(valuesTable,BorderLayout.CENTER);
        JPanel auxPanel = new JPanel();
        auxPanel.setLayout(new FlowLayout());
        auxPanel.add(back,BorderLayout.SOUTH);
        auxPanel.add(deleteAccount,BorderLayout.SOUTH);
        mainPanel.add(auxPanel,BorderLayout.SOUTH);

        return mainPanel;
    }

    /**
     * this method adds an action listener to all the buttons that are
     * created in this class
     * @param listener the action lister to be assigned
     */
    public void registerManageSongListeners(ActionListener listener){
        back.setActionCommand(BACK);
        back.addActionListener(listener);

        deleteAccount.setActionCommand(DELETE_ACCOUNT);
        deleteAccount.addActionListener(listener);
    }
}
