package view;

import javax.swing.*;

/**
 * Class for displaying a pop up with an informative message to the user
 * <p>
 * @author Arcadia Youlten
 * @version %I% %G%
 */

public class DialogBoxView {

    /**
     * Method for creating a pop up with an informative message for the user
     *
     * @param infoMessage   Message to be displayed
     * @param titleBar      The text the new window will have
     */

    public static void popup(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

}
