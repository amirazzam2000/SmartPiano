package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * This is a class to create the Register view. It contains different fields,
 * username, email, and password, that allow the user to enter their
 * information. More over, the client has the option to login or register by
 * clicking the corresponding buttons.
 *
 * @author Nicole Leser
 * @see Controller.RegisterUserController
 * @see ActionListener
 *
 */
public class RegisterUserView{
    public static final String BACK = "Back";
    public static final String REGISTER = "Register";
    private JPanel register_panel;
    private JLabel username, password, rePassword,email;
    private JTextField userName_TextField;
    private JTextField email_TextField;
    private JPasswordField pass_TextField;
    private JPasswordField rePass_TextField;
    private final JButton register;
    private final JButton back;

    /**
     * Constructor method that includes two JButtons: register and back
     */
    public RegisterUserView(){
        register = new JButton("Register");
        Image image = ViewUtils.getScaledImageFromPath("Shared/src/view" +
                "/assets/noun_back_1227057.bmp", null, 25, 25);
        back = new JButton(new ImageIcon(image));
    }

    /**
     * This method is used to create a JPanel to illustrate the Register user
     * view. This view has two buttons: register and back button. Also, it has
     * text fields so the user can enter their information.
     * @return a JPanel with the Register view
     */
    public JPanel registerUserView(){

        register_panel = new JPanel(new GridLayout(5, 1));
        username = new JLabel();
        password = new JLabel();
        rePassword = new JLabel();
        email = new JLabel();

        username.setText("Username:");
        password.setText("Password:");
        rePassword.setText("Repeat Password:");
        email.setText("Email:");

        userName_TextField = new JTextField();
        rePass_TextField = new JPasswordField();
        pass_TextField = new JPasswordField();
        email_TextField = new JTextField();

        register_panel.add(username);
        register_panel.add(userName_TextField);
        register_panel.add(email);
        register_panel.add(email_TextField);
        register_panel.add(password);
        register_panel.add(pass_TextField);
        register_panel.add(rePassword);
        register_panel.add(rePass_TextField);
        register_panel.add(back);
        register_panel.add(register);

        return register_panel;
    }


    /**
     * Method to set and add the action listeners for the back button and
     * register actions.
     * @param listener indicates the action listener to be added
     */
    public void registerActionListener(ActionListener listener) {
        back.setActionCommand(BACK);
        register.setActionCommand(REGISTER);

        back.addActionListener(listener);
        register.addActionListener(listener);
    }

    /**
     * Gets the information entered by the user in the text fields on the
     * screen in the {@link RegisterUserView}
     * @return a string with three fields:
     * <p>the first is the username,
     * <p>the second is the email,
     * <p>the third is the password
     * <p>the forth is the password repeated
     */
    public String[] getInput() {
        String[] input = new String[4];
        input[0] = userName_TextField.getText();
        input[1] = email_TextField.getText();
        input[2] = String.valueOf(pass_TextField.getPassword());
        input[3] = String.valueOf(rePass_TextField.getPassword());
        return input;
    }
}
