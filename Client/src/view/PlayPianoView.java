package view;

import Controller.PianoKeysController;
import Server.CommunicationServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/** This class creates the view for the Play Piano functionality. It contains three buttons: back, record, and settings.
 * It also contains which octave can be played in the keyboard keys. This class creates the interface for the piano.
 *
 * @author Amir Azzam and Sonia Leal
 * @see PianoKeysController
 * @see CommunicationServer
 * @see ActionEvent
 * @see ActionListener
 */
public class PlayPianoView {
    public static final String BACK = "Back";
    public static final String RECORD = "Record";
    public static final String SETTINGS = "Settings";

    private final JButton record;
    private final JButton back;
    private final JButton keyboardSettings;
    private final String[] octave = {"4","5"};
    private PianoKeysController actionListener;

    /**
     *Constructor for the class which creates and sets up the buttons.
     */
    public PlayPianoView(){
        Image image = ViewUtils.getScaledImageFromPath(ViewUtils.BACK, null, 25,
                25);
        back = new JButton(new ImageIcon(image));
        back.setBackground(Color.WHITE);
        image = ViewUtils.getScaledImageFromPath(ViewUtils.RECORD, null, 25, 25);
        record = new JButton(new ImageIcon(image));
        record.setBackground(Color.WHITE);
        image = ViewUtils.getScaledImageFromPath(ViewUtils.SETTINGS, null, 25, 25);
        keyboardSettings = new JButton(new ImageIcon(image));
        keyboardSettings.setBackground(Color.WHITE);
    }

    /**
     * Changes the record button to the pause icon
     */
    public void pause(){
        Image image = ViewUtils.getScaledImageFromPath(ViewUtils.PAUSE, null,
                25,25);

        record.setIcon(new ImageIcon(image));
    }

    /**
     * Changes the record button to the record icon
     */
    public void record(){
        Image image = ViewUtils.getScaledImageFromPath(ViewUtils.RECORD, null,
                25,25);

        record.setIcon(new ImageIcon(image));
    }

    /**
     * this method adds an action listener to all the buttons that are
     * created in this class
     * @param actionListener the action lister to be assigned
     */
    public void registerController(PianoKeysController actionListener){
        back.setActionCommand(BACK);
        record.setActionCommand(RECORD);
        keyboardSettings.setActionCommand(SETTINGS);

        back.addActionListener(actionListener);
        record.addActionListener(actionListener);
        keyboardSettings.addActionListener(actionListener);
        this.actionListener = actionListener;
    }

    /**
     * This methods create the panel for Play the Piano functionality. It holds the the keyboard and other options such
     * as change octave, record, and settings.
     * @return a JPanel that contains all the elements for the Play Piano.
     */
    public JPanel pianoKeys(){
        JPanel panel = new JPanel();

        panel.setLayout(new BorderLayout());
        JPanel top = new JPanel();
        //top.setPreferredSize(new Dimension(300*NUM_OCTAVES, 42));
        top.setLayout(new BoxLayout(top,BoxLayout.X_AXIS));
        //top.setLayout(new FlowLayout());
        top.setPreferredSize(new Dimension(300 * PianoKeys.NUM_OCTAVES,40));

        JLayeredPane keyBoard = new JLayeredPane();
        keyBoard.setPreferredSize(new Dimension(300 * PianoKeys.NUM_OCTAVES,
                168));
        JLabel label = new JLabel("Keyboard Octave:");
        JComboBox<String> octaves = new JComboBox<>(octave);
        octaves.setSelectedItem(0);
        octaves.setPreferredSize(new Dimension(20,10));
        octaves.setActionCommand(PianoKeys.OCTAVE_SELECTION);
        octaves.addActionListener(actionListener);

        panel.add(MainView.getPianoKey(), BorderLayout.SOUTH);

        back.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        record.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        top.add(Box.createHorizontalStrut(40));
        top.add(back);
        top.add(Box.createHorizontalStrut(20));
        top.add(label);
        top.add(octaves);
        top.add(Box.createHorizontalStrut((int) (95*PianoKeys.NUM_OCTAVES)));
        top.add(record);
        top.add(keyboardSettings);
        top.add(Box.createHorizontalStrut(40));

        panel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
        panel.add(top, BorderLayout.NORTH);

        // Return the keyboard
        return panel;
    }
}
