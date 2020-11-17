package model;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Config class defines the structure of the configuration file.
 */

public class Config {

    public static final int REGISTER_CLIENT_PROTOCOL = 1;
    public static final int LOGIN_CLIENT_PROTOCOL = 2;
    public static final int SHARE_SONG_PROTOCOL = 3;
    public static final int DISCONNECT = 4;
    public static final int LOGOUT = 5;
    public static final int GET_SONGS = 6;
    public static final int PLAY_SONG = 7;
    public static final int GET_FRIENDS = 8;
    public static final int ADD_FRIENDS = 9;
    public static final int DELETE_CURRENT_USER = 10;
    public static final int GET_FRIENDS_SONGS = 12;
    public static final int ACK = 69;

    private int port_db;
    private String address;
    private String name;
    private String user;
    private String password;
    private int port_client;
    private int global_ID_count;

    /**
     * fills the different class fields with the content of the json file
     */

    public void InitConfig() {
        String  configJson = null;
        try {
            configJson = Files.readString(Paths.get("Shared/resources/config.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Config config = gson.fromJson(configJson, Config.class);
        this.address = config.getAddress();
        this.name = config.getName();
        this.password = config.getPassword();
        this.port_client = config.getPort_client();
        this.port_db = config.getPort_db();
        this.user = config.getUser();
        this.global_ID_count = config.getGlobal_ID_count();
    }

    public int getGlobal_ID_count() {
        return global_ID_count;
    }
    public void updateGlobalCount(){
        global_ID_count++;

        HashMap<String,Object> map = new HashMap<>();
        map.put("port_db", getPort_db());
        map.put("address", getAddress());
        map.put("name",getName());
        map.put("password", getPassword());
        map.put("user", getUser());
        map.put("port_client", getPort_client());
        map.put("global_ID_count", getGlobal_ID_count());
        Gson gson = new Gson();
        try {
            FileWriter writer = new FileWriter("Shared/resources/config.json");
            gson.toJson(map, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort_db() {
        return port_db;
    }

    public void setPort_db(int port_db) {
        this.port_db = port_db;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort_client() {
        return port_client;
    }

    public void setPort_client(int port_client) {
        this.port_client = port_client;
    }

}
