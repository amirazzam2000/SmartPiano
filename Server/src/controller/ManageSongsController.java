package controller;

import model.SongExceptions.DefaultSongCanNotBeModifiedException;
import model.SongExceptions.SongIdNotFoundException;
import view.MainView;
import view.ManageSongView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for managing the actions performed on the ManageSongView class
 *
 * @see ManageSongView
 * @see java.awt.event.ActionListener
 * @see controller.MainController
 * <p>
 * @author Arcadia Youlten, Felipe Perez
 * @version %I% %G%
 */

public class ManageSongsController extends MainController implements ActionListener{

    private final int ColumnId = 2;
    private int selectedRow;

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case(MainView.BACK):
                gotoMainMenu();
                break;

            case(MainView.EDIT):

                selectedRow = getjTableManageSongs().getSelectedRow();

                DefaultTableModel model = (DefaultTableModel) getjTableManageSongs().getModel();

                if(selectedRow != -1) {
                    try {
                        if(Boolean.parseBoolean((String) model.getValueAt(selectedRow, 1))){
                            model.setValueAt("false",selectedRow, 1);
                            getSongDAO().changePermissionBySongId(Integer.parseInt((String) getjTableManageSongs().getValueAt(selectedRow, ColumnId)));

                        }else if(!Boolean.parseBoolean((String) model.getValueAt(selectedRow, 1))){
                            model.setValueAt("true" ,selectedRow, 1);
                            getSongDAO().changePermissionBySongId(Integer.parseInt((String) getjTableManageSongs().getValueAt(selectedRow, ColumnId)));
                        }

                    } catch (SongIdNotFoundException | DefaultSongCanNotBeModifiedException songIdNotFoundException) {
                        model.setValueAt("true" ,selectedRow, 1);
                        gotoPopupException();
                    }

                }

                break;

            case(MainView.DELETE):

                selectedRow = -1;

                selectedRow = getjTableManageSongs().getSelectedRow();

                if(selectedRow != -1) {
                    try {
                        getSongDAO().deleteSongById(Integer.parseInt((String) getjTableManageSongs().getValueAt(selectedRow, ColumnId)));
                        model= (DefaultTableModel) getjTableManageSongs().getModel();
                        model.removeRow(selectedRow);
                    } catch (SongIdNotFoundException | DefaultSongCanNotBeModifiedException songIdNotFoundException) {
                        gotoPopupException();
                    }

                }
                break;
        }
    }
}
