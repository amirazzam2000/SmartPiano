
import controller.*;
import model.*;
import server.MainServer;
import view.MainView;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Config config = new Config();
        config.InitConfig();

        UserDAO userManager = new UserDAO(config);
        SongDAO songManager = new SongDAO(config);

        MainView mainView = new MainView();

        MainController mainController = new MainController( songManager, mainView);

        mainController.assignListeners(new GraphController(), new MenuController(), new ManageSongsController(), new TopFiveController());

        //create the server with the configs
        MainServer server = new MainServer(config);

        //start the server
        server.startServer();
        userManager.disconnect();

    }
}
