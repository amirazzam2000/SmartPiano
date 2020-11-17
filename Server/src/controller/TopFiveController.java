package controller;

import view.MainView;
import view.MenuView;
import view.TopFiveView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for managing the actions performed on the TopFiveView class
 *
 * @see TopFiveView
 * @see java.awt.event.ActionListener
 * @see controller.MainController
 * <p>
 * @author Felipe Perez
 * @version %I% %G%
 */

public class TopFiveController extends MainController implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case(MainView.BACK):
                gotoMainMenu();
                break;
        }
    }
}
