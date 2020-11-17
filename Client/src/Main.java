import Controller.*;
import Server.CommunicationServer;
import view.KeyboardSettingsView;
import view.MainView;
import model.Config;

import java.awt.*;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        Config config = new Config();
        config.InitConfig();

        try{
            MainController controller = new MainController(new MainView());

            CommunicationServer cs = new CommunicationServer(config);

            RegisterUserController registerController =
                    new RegisterUserController(cs);
            MenuController menuController = new MenuController(cs);

            LogInUserController logInUserController = new LogInUserController(cs);

            PianoKeysController pianoKeysController = new PianoKeysController(cs);

            ChooseSongViewController chooseSongViewController =
                    new ChooseSongViewController(cs,pianoKeysController);

            FriendViewController friendViewController = new FriendViewController(cs);

            ManageAccountController manageAccountController =
                    new ManageAccountController(cs);

            KeyboardSettingsController keyboardSettingsController = new KeyboardSettingsController();
            cs.startConnection();

            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    controller.moveToLogIn();
                    controller.assignListeners(logInUserController,
                            registerController,
                            menuController,
                            pianoKeysController,
                            chooseSongViewController,
                            friendViewController,
                            manageAccountController,
                            keyboardSettingsController);
                }
            });
        }catch(java.net.ConnectException e){
            MainController.showErrorMessage("The server is down. Try again later.");
        }

    }
}
