package Controller;

import Server.CommunicationServer;
import com.google.gson.Gson;
import model.Song;
import model.SongExceptions.SongWrongFormatException;
import view.PianoKeys;
import view.PlayPianoView;
import view.SaveSongDialog;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;


/**
 * this is the controller to the {@link view.PianoKeys}
 *
 * it implements an action listener to respond to all the button clicks on
 * this views.
 *
 * @author Amir Azzam and Sonia Leal
 *
 * @see view.ManageAccountView
 * @see CommunicationServer
 * @see MainController
 * @see ActionListener
 */

public class PianoKeysController extends MainController implements ActionListener,
        MouseListener,
        KeyListener {
    private static boolean recording = false;
    private static final List<String> notes = Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B");
    private final CommunicationServer cs;
    private String octave;
    private Synthesizer synthesizer;
    private static boolean mute;
    private static boolean stop;
    /**
     * this attribute stores the keyboard keys that are associated with each of
     * the piano keys
     */
    private static HashMap<String, Character> hMap;
    private ArrayList<Note> clicks =new ArrayList<>();
    private StringBuilder jasonString;

    /**
     * this inner class store the information of a click on the piano keys to
     * be used later when exporting the song
     */
    private static class Note {
        private long timeStamp;
        private char trigger;
        private String note;
        private long duration;
        private static long time;


        public Note(long timeStamp, char trigger, String note) {
            this.timeStamp = timeStamp;
            this.trigger = trigger;
            this.note = note;
        }

        public static long getTime() {
            return time;
        }

        public static void setTime(long time) {
            Note.time = time;
        }

        public long getDuration() {
            return duration;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }

        public char getTrigger() {
            return trigger;
        }

        public void setTrigger(char trigger) {
            this.trigger = trigger;
        }


        @Override
        public String toString() {
            return "{" +
                    "timeStamp=" + timeStamp +
                    ", note=\"" + note + '\"' +
                    ", duration=" + duration +
                    '}';
        }
    }




    public PianoKeysController(CommunicationServer cs) {
        jasonString = new StringBuilder();
        createHashmapKeys();
        octave = "4";
        this.cs = cs ;
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, Character> gethMap() {
        return hMap;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        long time;
        switch (e.getActionCommand()){
            case PlayPianoView.SETTINGS:
                moveToKeyboardSettings();
                break;
            case PlayPianoView.BACK:
                moveToMenu();
                break;
            case PlayPianoView.RECORD:
                recording = !recording;
                if(recording){
                    changeRecordButtonToPause();
                    time = 0;
                    jasonString = new StringBuilder();
                    jasonString.append("[");
                }else {
                    changeRecordButtonToRecord();
                    jasonString.setCharAt(jasonString.length() - 1,']');
                    time =
                            (long)((System.nanoTime() -Note.getTime())/Math.pow(10,9));
                    String title = null;
                    boolean visibility =false;
                    do {
                        if (showSaveSongDialog() != JOptionPane.CANCEL_OPTION) {
                            title = getSongTitle();
                            visibility = getVisibility();
                            if(title == null)
                                showErrorMessage("you can't enter an empty " +
                                        "Title");
                            else{
                                try {
                                    cs.shareSong(new Song(title,jasonString.toString(),
                                            (float) time /60,null,visibility));
                                } catch (SongWrongFormatException songWrongFormatException) {
                                    showErrorMessage(songWrongFormatException.getMessage());
                                }
                            }
                        }else{
                            break;
                        }
                    }while(title == null);

                }
                break;
            case SaveSongDialog
                        .PLay:
                mute = false;
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        playSong(jasonString.toString());

                    }
                };
                (new Thread(r)).start();
            break;
            case PianoKeys.OCTAVE_SELECTION:
                if (e.getSource() instanceof JComboBox )
                octave =
                        (String)((JComboBox<String>)e.getSource()).getSelectedItem();
                break;
        }

    }

    /**
     * this method mutes the is currently playing song
     *
     */
    public void mute(){
        mute = true;
    }

    /**
     * this method unmute the song the is currently playing
     */
    public void unMute(){
        mute = false;
    }

    /**
     * this method checks if the song is muted or no
     * @return if the song is muted or no
     */
    public static boolean isMute() {
        return mute;
    }

    /**
     * this method plays a given song in the format of a jsonString
     * @param songString the song to be played
     */
    public void playSong(String songString){
        System.out.println(songString);
        Gson gson = new Gson();
        // parse the json string and store it in an array of notes
        Note[] notes = gson.fromJson(songString, Note[].class);

        // play each note
        for (int i = 0, notesLength = notes.length; i < notesLength; i++) {
            Note n = notes[i];
            try {
                if (i == 0)
                    Thread.sleep(n.timeStamp / 1000000);
                else
                    Thread.sleep(Math.abs(n.getTimeStamp() - notes[i-1].getTimeStamp() ) / 1000000);

                Timer timer = new Timer();

                if (!mute){
                    synthesizer.getChannels()[0].noteOn(id(n.note), 90);
                }
                changeKeyColor(n.note);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(!mute)
                            synthesizer.getChannels()[0].noteOff(id(n.note), 10);
                        resetKeyColor(n.note);
                    }
                }, (int) (n.getDuration() / 1000000));



            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * this method stops the currently playing song
     *
     */
    public synchronized static void stop() {
        PianoKeysController.stop = true;
    }

    /**
     * this method plays a given songs and notifies the callback when the song
     * is done
     * @param songString the song to be played in a jsonString format
     * @param musicCallbacks the callback class to be notified
     */
    public void playSong(String songString, MusicCallbacks musicCallbacks){
        System.out.println(songString);
        Gson gson = new Gson();
        mute = false;
        Note[] notes = gson.fromJson(songString, Note[].class);
        synchronized (new Thread()) {
            stop = false;
        }

        for (int i = 0, notesLength = notes.length; i < notesLength; i++) {
            Note n = notes[i];
            if(stop)
                break;
            try {
                if (i == 0)
                    Thread.sleep(n.timeStamp / 1000000);
                else
                    Thread.sleep(Math.abs(n.getTimeStamp() - notes[i-1].getTimeStamp()) / 1000000);

                Timer timer = new Timer();

                if (!mute){
                    synthesizer.getChannels()[0].noteOn(id(n.note), 90);
                }
                changeKeyColor(n.note);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(!mute)
                            synthesizer.getChannels()[0].noteOff(id(n.note), 10);
                        resetKeyColor(n.note);
                    }
                }, (int) (n.getDuration() / 1000000));



            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        musicCallbacks.onSongDone();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof  JPanel) {
            JPanel panel = (JPanel) e.getSource();
            panel.requestFocusInWindow();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        JButton jb;
        String name;
        if(getPianoPanel() != null)
            getPianoPanel().requestFocusInWindow();
        if (e.getSource() instanceof JButton) {
            jb = (JButton) e.getSource();
            name = jb.getName();
            String command = jb.getActionCommand();
            playNote(command);
            if (jasonString.toString().equals("[")) {
                clicks.add(new Note(0, ' ', command));
                Note.setTime(System.nanoTime());
            }
            else {
                clicks.add(new Note(System.nanoTime() - Note.getTime(), ' ',
                        command));
            }

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        synchronized (Thread.class) {
            long currentTime = System.nanoTime();
            for (int i = 0; i < clicks.size(); i++) {
                Note n = clicks.get(i);
                if (n.getTrigger() == ' ') {
                    synthesizer.getChannels()[0].noteOff(id(n.getNote()),10);
                    n.setDuration(currentTime - (n.getTimeStamp() + Note.getTime()));
                    jasonString.append(n.toString()).append(",");
                    clicks.remove(i);
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof  JPanel) {
            JPanel panel = (JPanel) e.getSource();
            panel.requestFocusInWindow();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * initialize the hashMap with the default keyboard keys to play the
     * piano keys
     */
    private void createHashmapKeys(){
        hMap = new HashMap<String, Character>();
        hMap.put("C", 'A');
        hMap.put("D", 'S');
        hMap.put("E", 'D');
        hMap.put("F", 'F');
        hMap.put("G", 'G');
        hMap.put("A", 'H');
        hMap.put("B", 'J');
        hMap.put("C#", 'W');
        hMap.put("D#", 'E');
        hMap.put("F#", 'R');
        hMap.put("G#", 'T');
        hMap.put("A#", 'Y');

    }

    /**
     * checks if the given keyboard key registered as a piano key or not
     * @param c the pressed keyboard key
     * @return the associated piano key if the keyboard key is registered or
     * null other wise
     */
    private String getHashMapKey(char c){
        for(Map.Entry<String, Character> entry : hMap.entrySet()){
            if(c == entry.getValue()) return entry.getKey();
        }
        return null;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        String keyPressed = getHashMapKey(String.valueOf(e.getKeyChar()).toUpperCase().charAt(0));

        if (keyPressed != null && octave != null) {
            keyPressed += octave;
            boolean donot = false;
            for (Note n :
                    clicks) {
                if (n.trigger == '-' && n.note.equals(keyPressed )) {
                    donot = true;
                    break;
                }
            }
            if(!donot) {
                playNote(keyPressed);

                if(jasonString.toString().equals("[")) {
                    clicks.add(new Note(0, '-', keyPressed));
                    Note.setTime(System.nanoTime());
                }else{
                    clicks.add(new Note(System.nanoTime() - Note.getTime(), '-',
                            keyPressed));
                }
            }
        }
    }

    /**
     * plays a given note using Java midi library
     * @param note the note to be played
     */
    private synchronized void playNote( String note){

                synthesizer.getChannels()[0].noteOn(id(note),90);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        synchronized (Object.class) {
            long currentTime = System.nanoTime();
            double timeTotal;
            for (int i = 0; i < clicks.size(); i++) {
                Note n = clicks.get(i);
                if (n.getTrigger() == '-') {
                    synthesizer.getChannels()[0].noteOff(id(n.getNote()),10);
                    n.setDuration(currentTime - (n.getTimeStamp() + Note.getTime()));
                    jasonString.append(n.toString()).append(",");
                    clicks.remove(i);
                }
            }
        }
    }

    /**
     * returns the id of the note that is used to identify the not in the
     * java midi library given a string note encapsulated in the format we
     * created
     * @param note the string note to be played
     * @return the java midi code for the note
     */
    private int id(String note)
    {
        int lastId;
        int octave;
        if(!(note.substring(1, 2).charAt(0) == '#')){
            octave = Integer.parseInt(note.substring(1, 2));
            lastId = notes.indexOf(note.substring(0,1)) + 12 * octave + 12;
        }else{
            octave = Integer.parseInt(note.substring(2, 3));
            lastId = notes.indexOf(note.substring(0,2)) + 12 * octave + 12;

        }
        return lastId;
    }

}
