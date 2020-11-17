package view;

import model.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class creates a view that allows the user to add friends, displays the user's friends, and select a friend to
 * see their songs.
 *
 * @author Felipe Perez
 *
 * @see Controller.FriendViewController
 * @see ActionListener
 * @see ArrayList
 */
public class FriendView {
    public static final String BACK = "Back";
    public static final String ADD = "Add";
    private JTextField searchField;
    private final JButton back;
    private final JButton add;
    private DefaultTableModel jTableFriendsModel;
    private JTable jTableFriends;
    private JPanel mainPanel;
    private ActionListener listener;

    /**
     *Constructor for the class which creates and sets up the buttons.
     */
    public FriendView() {
        Image image = ViewUtils.getScaledImageFromPath(ViewUtils.BACK, null, 25,
                25);
        back = new JButton(new ImageIcon(image));
        back.setBackground(Color.WHITE);

        add = new JButton("Add");
        add.setBackground(Color.WHITE);

        mainPanel = new JPanel();
    }

    /**
     * This method create a JPanel that displays the user's friends in a table and allows the user to select one to
     * be able to see the selected friend's songs. Moreover, this view allows to add friends.
     * The user is able to go back.
     * @param friends array list that contains the user's friends
     * @return JPanel containing all the information (table, text field, and buttons)
     */
    public JPanel friendView(ArrayList<User> friends){
        //main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        mainPanel.setPreferredSize(new Dimension(500,400));

        //search panel
        JPanel mainSearchPanel = new JPanel();
        mainSearchPanel.setLayout(new FlowLayout());

        JPanel panelSearchLeft = new JPanel();
        panelSearchLeft.setLayout(new FlowLayout());

        JPanel panelSearchRight = new JPanel();
        panelSearchRight.setLayout(new FlowLayout());
        
        JLabel searchFriends = new JLabel();
        searchFriends.setText("Add Friend By Code:");
        searchField = new JTextField();
        searchField.setColumns(11);

        panelSearchLeft.add(searchFriends);
        panelSearchLeft.add(searchField);
        panelSearchRight.add(add);

        mainSearchPanel.add(panelSearchLeft);
        mainSearchPanel.add(panelSearchRight);

        // Column Names
        String[] columnNames = { "User Name" , "User ID"};

        String[][] ownFriends =
                new String[friends.size()][2];
        for (int i = 0; i < friends.size(); i++) {
            ownFriends[i][0] = friends.get(i).getNickname();
            ownFriends[i][1] = String.valueOf(friends.get(i).getCode());
        }

        jTableFriendsModel = new DefaultTableModel(ownFriends, columnNames);
        jTableFriends = new JTable(jTableFriendsModel){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        jTableFriends.getTableHeader().setReorderingAllowed(false);

        jTableFriends.getSelectionModel().addListSelectionListener((ListSelectionListener) listener);

        JScrollPane sp = new JScrollPane(jTableFriends);

        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jPanel.add(back);

        mainPanel.add(mainSearchPanel);
        mainPanel.add(sp);
        mainPanel.add(jPanel);

        return mainPanel;
    }

    /**
     * Method to set and add the action listeners for the back button and
     * register actions.
     * @param listener indicates the action listener to be added
     */
    public void registerFriendViewListeners(ActionListener listener){
        back.setActionCommand(BACK);
        back.addActionListener(listener);

        add.setActionCommand(ADD);
        add.addActionListener(listener);

        this.listener = listener;
    }

    public String getInput() {
        String input;
        input = searchField.getText();
        return input;
    }

    public JTable getJTableFriends() {
        return jTableFriends;
    }

    /**
     * This method updates the current view once the user has added a new friend. Thus, the new friend added can be
     * seen in the table.
     * @param friends an array list containing all the friends of the user
     */
    public void updateFriends(ArrayList<User> friends){
        //main panel
        mainPanel.removeAll();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        mainPanel.setPreferredSize(new Dimension(500,400));

        //search panel
        JPanel mainSearchPanel = new JPanel();
        mainSearchPanel.setLayout(new FlowLayout());

        JPanel panelSearchLeft = new JPanel();
        panelSearchLeft.setLayout(new FlowLayout());

        JPanel panelSearchRight = new JPanel();
        panelSearchRight.setLayout(new FlowLayout());

        JLabel searchFriends = new JLabel();
        searchFriends.setText("Add Friend By Code:");
        searchField = new JTextField();
        searchField.setColumns(11);

        panelSearchLeft.add(searchFriends);
        panelSearchLeft.add(searchField);
        panelSearchRight.add(add);

        mainSearchPanel.add(panelSearchLeft);
        mainSearchPanel.add(panelSearchRight);

        // Column Names
        String[] columnNames = { "User Name" , "User ID"};

        String[][] ownFriends =
                new String[friends.size()][2];
        for (int i = 0; i < friends.size(); i++) {
            ownFriends[i][0] = friends.get(i).getNickname();
            ownFriends[i][1] = String.valueOf(friends.get(i).getCode());
        }

        jTableFriendsModel = new DefaultTableModel(ownFriends, columnNames);
        jTableFriends = new JTable(jTableFriendsModel){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        jTableFriends.getSelectionModel().addListSelectionListener((ListSelectionListener) listener);

        JScrollPane sp = new JScrollPane(jTableFriends);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jPanel.add(back);

        mainPanel.add(mainSearchPanel);
        mainPanel.add(sp);
        mainPanel.add(jPanel);

    }
}
