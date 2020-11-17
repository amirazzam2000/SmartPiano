package view;

import Controller.PianoKeysController;
import Server.CommunicationServer;

import javax.swing.*;
import java.awt.*;
import java.awt.desktop.QuitEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * This class constructs the piano keys view. It includes the piano keys and the octave selection for the keyboard.
 *
 * @author Amir Azzam and Sonia Leal
 * @see PianoKeysController
 * @see CommunicationServer
 * @see ActionEvent
 * @see ActionListener
 */
public class PianoKeys{
    public static final String OCTAVE_SELECTION = "Octave Selection";
    public static final int NUM_OCTAVES = 2;
    private final String[] notes = {"C","D","E","F","G","A","B"};
    private final String[] sharps = {"C#","D#","F#","G#","A#"};
    private final String[] octave = {"4","5"};
    private PianoKeysController actionListener;
    private HashMap<String,JButton> keyboardKeys;
    private static JPanel panel;


    public PianoKeys(){
        keyboardKeys = new HashMap<>();

    }

    /**
     * this method adds an action listener to the PianoKeysController instance
     * @param actionListener the action lister to be assigned
     */
    public void registerController(PianoKeysController actionListener){
        this.actionListener = actionListener;
        pianoKeys();
    }

    public static JPanel getPanel() {
        return panel;
    }

    /**
     * This method constructs the JPanel that contains the piano keys and the octave selection. In this method the
     * keys are set to its default color (white or black). Additionally, listeners are attach to know when a key and
     * been pressed.
     * @return a JPanel that has the piano keys and the octave selection
     */
    public JPanel pianoKeys(){
        panel = new JPanel();

        panel.setFocusable(true);
        panel.addMouseListener(actionListener);
        panel.addKeyListener(actionListener);

        JLayeredPane keyBoard = new JLayeredPane();
        keyBoard.setPreferredSize(new Dimension(300 * NUM_OCTAVES,168));

        String name;
        int x = 55;
        int y = 0;
        // Add the white key buttons
        for(int i=0; i< NUM_OCTAVES; i++){
            for(int j=0; j<7; j++){
                JButton jb = new JButton();
                jb.setBackground(Color.WHITE);
                name = notes[j]+octave[i];
                jb.setName(name);
                jb.setActionCommand(name);
                jb.addMouseListener(actionListener);

                jb.setBounds(x,y,35,162);
                keyBoard.add(jb,Integer.valueOf(1));
                keyBoard.add(Box.createRigidArea(new Dimension(2, 0)));
                x += 37;
                keyboardKeys.put(name,jb);
            }
        }


        int[] distances = {77,115,188,226,264};
        // Add the black keys
        for(int i=0; i< NUM_OCTAVES; i++){
            for (int j = 0; j < 5; j++) {
                JButton jb0 = new JButton();
                name = sharps[j]+octave[i];
                jb0.setName(name);
                jb0.setBackground(Color.BLACK);
                jb0.setActionCommand(name);
                jb0.addMouseListener(actionListener);

                jb0.setBounds(distances[j]+(260*i),y,25,95);
                keyBoard.add(jb0,Integer.valueOf(2));
                keyboardKeys.put(name,jb0);
            }
        }

        panel.add(keyBoard);

        // Return the keyboard
        return panel;
    }


    /**
     * Changes the color of the key (indicated as a parameter) to red which means that it is currently playing.
     * @param keyId id of the key whose color will be changed
     */
    public void changeKeyColor(String keyId){
        keyboardKeys.get(keyId).setBackground(Color.red);
    }

    /**
     * Resets the colors depending on what type of key it is. The possible colors that a key can take once it has
     * been reset is black or white.
     * @param keyId if og the key whose color will be changed to its default color
     */
    public void resetKeyColor(String keyId){
        if(keyId.contains("#")){
            keyboardKeys.get(keyId).setBackground(Color.BLACK);
        }
        else{
            keyboardKeys.get(keyId).setBackground(Color.WHITE);
        }
    }

}
