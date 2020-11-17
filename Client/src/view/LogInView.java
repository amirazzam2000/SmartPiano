package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * This is a class to create the log in view. It contains different fields, username and password, that allow the user
 * to enter their credentials. More over, the client has the option to login or register by clicking the
 * corresponding methods.
 *
 * @author Nicole Leser
 * @see Controller.LogInUserController
 * @see ActionListener
 */

public class LogInView{
    public static final String LOGIN = "Login";
    public static final String REGISTER = "Register";
    private JPanel login_panel;
    private JLabel username, password;
    private JTextField userName_TextField;
    private JPasswordField pass_TextField;
    private final JButton login;
    private final JButton blueLabel;

    /**
     * Constructor method that includes two JButtons: register and login
     */
    public LogInView(){
        blueLabel = new JButton("Register");
        login = new JButton("Login");
    }

    /**
     * This method is used to create a JPanel to illustrate the log in view. This view has two buttons:
     * register and login. Also, it has text fields so the user can enter their credentials.
     * @return a JPanel with the log in view
     */
    public JPanel LogInView(){

        login_panel = new JPanel(new GridLayout(3, 2));

        username = new JLabel();
        password = new JLabel();

        username.setText("Username :");
        password.setText("Password :");

        userName_TextField = new JTextField();
        pass_TextField = new JPasswordField();



        login_panel.add(username);
        login_panel.add(userName_TextField);
        login_panel.add(password);
        login_panel.add(pass_TextField);
        login_panel.add(blueLabel);
        login_panel.add(login);

        return login_panel;
    }

    /**
     * Method to set and add the action listeners for the login and register actions.
     * @param listener indicates the action listener to be added
     */
    public void registerActionListener(ActionListener listener){
        login.setActionCommand(LOGIN);
        blueLabel.setActionCommand(REGISTER);

        login.addActionListener(listener);
        blueLabel.addActionListener(listener);
    }

    /**
     * It is used to get the information the user has put for their username/email and password
     * @return a String array of size 2. The username/email is in position 0 and the password in
     * position 1.
     */
    public String[] getInput() {
        String[] input = new String[2];
        input[0] = userName_TextField.getText();
        input[1] = String.valueOf(pass_TextField.getPassword());
        return input;
    }


}
