package controller;
import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for managing the actions performed on the GraphView class
 *
 * @see GraphView
 * @see java.awt.event.ActionListener
 * @see controller.MainController
 * <p>
 * @author Arcadia Youlten, Felipe Perez
 * @version %I% %G%
 */

public class GraphController extends MainController implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case(MainView.BACK):
                gotoMainMenu();
                break;
        }
    }
}
