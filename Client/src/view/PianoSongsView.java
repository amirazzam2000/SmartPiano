package view;

import Controller.PianoKeysController;
import Server.CommunicationServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class corresponds to the screen displayed when the user wants to play a song. This view contains a piano,
 * a drop down that allows to change the octave of the keyboard keys, and two buttons: one to mute the recording and
 * the other button allows the user to go back.
 *
 * @author Amir Azzam and Sonia Leal
 * @see PianoKeysController
 * @see CommunicationServer
 * @see ActionEvent
 * @see ActionListener
 */
public class PianoSongsView {
    public static final String HIDE = "hide";
    public static final String MUTE = "mute";
    private final JButton mute;
    private final JButton back;
    private final String[] octave = {"4","5"};
    private ActionListener actionListener;

    /**
     *Constructor for the class that creates and sets up the buttons.
     */
    public PianoSongsView(){
        Image image = ViewUtils.getScaledImageFromPath(ViewUtils.BACK, null,
                25,
                25);
        back = new JButton(new ImageIcon(image));
        back.setBackground(Color.WHITE);
        image = ViewUtils.getScaledImageFromPath(ViewUtils.MUTE, null, 25, 25);
        mute = new JButton(new ImageIcon(image));
        mute.setBackground(Color.WHITE);
    }

    /**
     * Method to set the icon of the button to unmute
     */
    public void unMute(){
        Image image = ViewUtils.getScaledImageFromPath(ViewUtils.UNMUTE, null,
                25,25);

        mute.setIcon(new ImageIcon(image));
    }

    /**
     * Method to set the icon of the button to mute
     */
    public void mute(){
        Image image = ViewUtils.getScaledImageFromPath(ViewUtils.MUTE, null,
                25,25);

        mute.setIcon(new ImageIcon(image));
    }
    /**
     * this method adds an action listener to all the buttons that are
     * created in this class
     * @param actionListener the action lister to be assigned
     */
    public void registerController(ActionListener actionListener){
        back.setActionCommand(HIDE);
        mute.setActionCommand(MUTE);

        back.addActionListener(actionListener);
        mute.addActionListener(actionListener);
        this.actionListener = actionListener;
    }


    /**
     * Method that constructs the jPanel with the piano keys, options and buttons to go back and mute/unmute.
     * @param keyboard panel that includes the keyboard to be displayed
     * @return the jPanel containing all the elements previously described
     */
    public JPanel pianoKeys(JPanel keyboard){
        JPanel panel = new JPanel();

        panel.setLayout(new BorderLayout());
        JPanel top = new JPanel();
        //top.setPreferredSize(new Dimension(300*NUM_OCTAVES, 42));
        top.setLayout(new BoxLayout(top,BoxLayout.X_AXIS));
        //top.setLayout(new FlowLayout());
        top.setPreferredSize(new Dimension(300 * PianoKeys.NUM_OCTAVES,40));
        panel.setFocusable(true);
        keyboard.setFocusable(true);

        JLabel label = new JLabel("Keyboard Octave:");
        JComboBox<String> octaves = new JComboBox<>(octave);
        octaves.setSelectedItem(0);
        octaves.setPreferredSize(new Dimension(20,10));
        octaves.setActionCommand(PianoKeys.OCTAVE_SELECTION);
        octaves.addActionListener(actionListener);

        panel.add(MainView.getPianoKey(), BorderLayout.SOUTH);

        back.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        mute.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        top.add(Box.createHorizontalStrut(40));
        top.add(back);
        top.add(Box.createHorizontalStrut(20));
        top.add(label);
        top.add(octaves);
        top.add(Box.createHorizontalStrut((int) (100*PianoKeys.NUM_OCTAVES)));
        top.add(mute);
        top.add(Box.createHorizontalStrut(40));

        panel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
        panel.add(top, BorderLayout.NORTH);

        panel.add(keyboard);

        // Return the keyboard
        return panel;
    }
}
