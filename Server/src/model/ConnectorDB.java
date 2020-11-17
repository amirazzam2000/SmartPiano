package model;

import java.sql.*;

/**
 * Class for connecting to the MYSQL localhost database,
 * where the songs and users will be stored (in the server's case)
 */

public class ConnectorDB {
    private static String userName;
    private static String password;
    private static String db;
    private static int port;
    private static String url = "jdbc:mysql://localhost";
    private static Connection conn = null;
    private static Statement s;

    /**
     * Constructor method that initializes de information needed to connect to the database
     *
     * @param usr
     * @param pass
     * @param db
     * @param port
     */

    public ConnectorDB(String usr, String pass, String db, int port) {
        ConnectorDB.userName = usr;
        ConnectorDB.password = pass;
        ConnectorDB.db = db;
        ConnectorDB.port = port;
        ConnectorDB.url += ":"+port+"/";
        ConnectorDB.url += db;
        ConnectorDB.url += "?verifyServerCertificate=false&useSSL=false";
    }

    /**
     * Method that attempts to connect to the database
     * @return if connection successful or not
     */

    public boolean connect() {
        try {
            Class.forName("com.mysql.jdbc.Connection");

            conn = (Connection) DriverManager.getConnection(url, userName, password);
            if (conn != null) {
                System.out.println("Connection to the database "+url+" ... Ok");
            }
            return true;
        }
        catch(SQLException ex) {
            System.out.println("Problems occurred when attempting to connect to the database  --> "+url);
        }
        catch(ClassNotFoundException ex) {
            System.out.println(ex);
        }
        return false;

    }

    /**
     * Method for insert queries
     * @param query insert query to be queried
     */

    public void insertQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problems occured when attempting to inserting query --> " + ex.getSQLState());
        }
    }

    /**
     * Method for update queries
     * @param query update query to be queried
     */

    public void updateQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problems occured when attempting to updating query --> " + ex.getSQLState());
        }
    }

    /**
     * Method for delete queries
     * @param query delete query to be queried
     */

    public void deleteQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println(" --> " + ex.getSQLState());
        }

    }

    /**
     * Method for select queries
     *
     * @param query select query to be queried
     * @return The result from the select query
     */

    public ResultSet selectQuery(String query){
        ResultSet rs = null;
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery (query);

        } catch (SQLException ex) {
            System.out.println("Problems occured when attempting to obtain data --> " + ex.getSQLState());
        }
        return rs;
    }

    /**
     * Method that attempts to disconnect to the database.
     * Throws an exception if disconnection was unsuccessful
     */

    public void disconnect(){
        try {
            conn.close();
            System.out.println("Sucessfully disconnected!");
        } catch (SQLException e) {
            System.out.println("Problems occured when attempting to close the session --> " + e.getSQLState());
        }
    }

}

