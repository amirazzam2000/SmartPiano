package view;

import Controller.PianoKeysController;
import Server.CommunicationServer;
import Server.CommunicationServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** This class creates the panel that displays which keyboard keys are the ones currently configured to its
 * corresponding note and allows the user the select and change the keys. This panel contains a table with the
 * aforementioned information as well as two buttons that allows us to know if the user does not want to change
 * the settings (back button) or if they would like to save the current / modified settings (by pressing the save button).
 *
 * @author Sonia Leal
 * @see PianoKeysController
 * @see CommunicationServer
 * @see ActionEvent
 * @see ActionListener
 */

public class KeyboardSettingsView {

    public static final String SAVE = "Save";
    public static final String BACK = "Back";
    private final JButton save;
    private final JButton back;
    private JTable table;

    /**
     * Constructor for the class that sets the buttons
     */
    public KeyboardSettingsView() {
        this.save = new JButton();
        save.setText(SAVE);
        save.setBackground(Color.WHITE);
        save.setPreferredSize(new Dimension(150, 35));

        Image image = ViewUtils.getScaledImageFromPath(ViewUtils.BACK, null, 25,
                25);
        back = new JButton(new ImageIcon(image));
        back.setBackground(Color.WHITE);
    }

    public JTable getTable() {
        return table;
    }

    /**
     * This methods creates the main panel which contains the table and buttons. It displays the current settings in
     * the table and allows the user to click on the "Keyboard" column to change the configuration if it is desired.
     * @return JPanel containing all the information for the view
     */
    public JPanel settingsPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(450, 300));
        mainPanel.setLayout(new BorderLayout());

        String[] columnNames = {"Note", "Keyboard"};
        String[] keyNames = {"C", "D", "E", "F", "G", "A", "B", "C#", "D#", "F#", "G#", "A#"};
        String[][] keys = new String[keyNames.length][keyNames.length];
        for (int i = 0; i < keyNames.length; i++) {
            keys[i][0] = keyNames[i];
            keys[i][1] = String.valueOf(PianoKeysController.gethMap().get(keyNames[i]));
        }

        table = new JTable(keys, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };

        table.getTableHeader().setReorderingAllowed(false);
        JLabel text = new JLabel("Choose which keys you would like to be able to play with the keyboard.");

        table.setPreferredSize(new Dimension(200, 250));
        JScrollPane sp = new JScrollPane(table);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(sp, BorderLayout.CENTER);
        mainPanel.add(text, BorderLayout.NORTH);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jPanel.add(back, BorderLayout.SOUTH);
        jPanel.add(save, BorderLayout.SOUTH);
        mainPanel.add(jPanel, BorderLayout.SOUTH);
        return mainPanel;
    }


    /**
     * This methods registers the action commands and listeners for the Save and Back buttons.
     * @param listener the event that occurs
     */
    public void registerKeyboardSettingsListeners(ActionListener listener){
        save.setActionCommand(SAVE);
        back.setActionCommand(BACK);

        save.addActionListener(listener);
        back.addActionListener(listener);

    }

}
