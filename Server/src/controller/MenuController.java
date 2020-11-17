package controller;

import view.MainView;
import view.MenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for managing the actions performed on the MenuView class
 *
 * @see MenuView
 * @see java.awt.event.ActionListener
 * @see controller.MainController
 * <p>
 * @author Felipe Perez
 * @version %I% %G%
 */

public class MenuController extends MainController implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case (MainView.MANAGE_SONGS):
                gotoManageSongs();
                break;

            case (MainView.TOP_FIVE_SONGS):
                gotoTopFive();
                break;

            case (MainView.DISPLAY_GRAPHS):
                gotoGraphs();
                break;
        }
    }
}
