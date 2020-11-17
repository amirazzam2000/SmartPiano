package server;

import model.Config;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


/**
 * MainServer class defines server
 *  @author Felipe Perez
 *  @version %I% %G%
 *
 *  @see ClientDedicatedServer
 *  @see Config
 *
 *  */
public class MainServer {

    private final ArrayList<ClientDedicatedServer> dedicatedServers;

    private final Config config;
    private ServerSocket serverSocket;
    private boolean isRunning;

    public MainServer(Config config) {
        this.dedicatedServers = new ArrayList<>();
        this.config = config;
    }

    /**
     * Starts the server, listening for client connections
     */
    public void startServer() {
        try {
            serverSocket = new ServerSocket(config.getPort_client());
            isRunning = true;

            while (isRunning) {
                System.out.println("Waiting for a client...");
                Socket socket = serverSocket.accept();

                System.out.println("Client connected");
                ClientDedicatedServer dServer = new ClientDedicatedServer(socket, dedicatedServers);
                dedicatedServers.add(dServer);
                dServer.start();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
