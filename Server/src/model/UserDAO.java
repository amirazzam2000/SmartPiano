package model;

import model.UserExceptions.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *this class's main purpose is to connect to the database and get User objects. Moreover,
 * it offers methods to add/delete users, get the a user's
 * information, and finally assign multiple users as friends.
 * <p>
 *
 *
 * @author Amir Azzam
 * @version %I% %G%
 *
 * @see ConnectorDB
 * @see ResultSet
 * @see Config
 * @see User
 * */
public class UserDAO {
    private static final int code = 1;
    private static ConnectorDB connector;
    private boolean connected;
    private static Config config;


    /**
     *  this method is used to initialize the UserDAO using the configuration
     *  of the database, so we can connect to the database.
     * @param configuration an object that contains the info of the database
     *                      name, port, user name, and the password to connect.
     *
     * @see Config
     * @see ConnectorDB
     */
    public UserDAO( Config configuration) {
        connector = new ConnectorDB(configuration.getUser(), configuration.getPassword(),
                configuration.getName(),
                configuration.getPort_db());
        connected = connector.connect();
        System.out.println(connected);
        UserDAO.config = configuration;
    }

    /**
     * a default constructor to be able to have an instance of this class and
     * access it's methods from other classes
     */
    public UserDAO(){

    }

    /**
     *  this class registers a new user in the system and adds them to the
     *  database.
     * @param user the user to be added to the database, the user doesn't
     *             need to have a code as this code will be assigned to them
     *             after the user is added to the database.
     *
     * @return it returns a boolean saying wither the the user has been added
     * successfully or not
     * @throws UsernameAlreadyExistsException if the nickname already is
     * repeated and already exists in the database
     * @throws EmailAlreadyExistsException if the email already is
     * repeated and already exists in the database
     *
     * */
    public synchronized boolean registerUser(User user) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        try {
            ResultSet resultSet = connector.selectQuery("select email from " +
                    "users where email " +
                    "like '" + user.getEmail()+ "'");
            ResultSet resultSet2 = connector.selectQuery("select nickname " +
                    "from users where nickname " +
                    "like '" + user.getNickname()+ "'");

            if(resultSet2.next())
                throw new UsernameAlreadyExistsException();
            else if(resultSet.next())
                throw new EmailAlreadyExistsException();
            else {
                connector.insertQuery("insert into users ( code ,nickname, " +
                        "email, " +
                        "password )" +
                        "values ('"+ user.getNickname().charAt(0) + (config.getGlobal_ID_count()) +
                        "' , '" + user.getNickname() +
                        "','" + user.getEmail() +
                        "','" + user.getPassword() +
                        "')");
                resultSet = connector.selectQuery("select * from " +
                        "users where email " +
                        "like '" + user.getEmail()+ "'");
                if(resultSet.next())
                    user.setCode(resultSet.getString("code"));

                config.updateGlobalCount();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * this method checks the credentials of the user
     *
     * @param username the nickname or the login of the user attempting to
     *                 login
     * @param password the password of the user attempting to login
     * @return the id of the user if the credentials were valid, or -1 if
     * the login was not successful
     * @throws UserNameNotFoundException if the the nickname/email didn't
     * exist in the database
     * @throws WrongPasswordException if the password didn't match the
     * nickname/email provided
     *
     *
     * */
    public synchronized String authenticateUser(String username,
                                                String password) throws UserNameNotFoundException, WrongPasswordException {
        ResultSet resultSet = connector.selectQuery("select * from users " +
                "where nickname like '" +username + "' or email like '" +username + "'");
        try {
            if(resultSet != null && resultSet.next()){
                String code = resultSet.getString("code");
                String query = "select * from users where " +
                        " code like '"+ code +
                        "' and password like '"+password+"'";
                ResultSet resultSet1 = connector.selectQuery(query);
                if (resultSet1 != null && resultSet1.next()){
                    return resultSet.getString("code");
                }
                else {
                    throw new WrongPasswordException();
                }
            }
            else {
                throw new UserNameNotFoundException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * this method gets the information of a user by their Id
     *
     * @param code the id of the user
     * @return the information of the user as {@link User} object
     * @throws UserNotFoundException if the id provided didn't correspond to
     * any of the users registered in the system
     *
     * @see User
     * */
    public synchronized User getUserById(String code) throws UserNotFoundException {
        ResultSet resultSet = connector.selectQuery("select * from users " +
                "where code  like '" + code + "'");
        User user = null;
        try {
            if(resultSet.next()){
            user = new User(resultSet.getString("nickname"),
                    resultSet.getString("email"), resultSet.getString(
                            "password"));
            user.setCode(resultSet.getString("code"));
            }else {
                throw new UserNotFoundException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    /**
     * this method updates the information of a user in the database
     *
     * @param user the user that contains the new information
     * @throws UserNotFoundException if the id provided didn't correspond to
     * any of the users registered in the system
     *
     * @see User
     * */
    public synchronized void editUser(User user) throws UserNotFoundException {
        ResultSet resultSet = connector.selectQuery("select * from users " +
                "where code  like '" + user.getCode() + "'");
        try {
            if(resultSet.next()){

                connector.updateQuery("UPDATE users SET nickname = '" +user.getNickname()+
                        "', email = '"+user.getEmail()+"', password = '"+user.getPassword()+"'" +
                        "WHERE  code like '"+user.getCode()+"'");

            }else {
                throw new UserNotFoundException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * links the two users provided together as friends
     * @param idCurrentUser the id of the user who made the request
     * @param idFriend the id of the user that the current user will be
     *                 linked to
     * @return a boolean indicating if the process was successful
     * @throws UserNotFoundException if any of the IDs provided doesn't
     * correspond to a user in the database
     *
     * */
    public synchronized boolean addFriendById(String idCurrentUser, String idFriend) throws UserNotFoundException, SameUserException {

        if (!idCurrentUser.equals(idFriend)) {
            ResultSet set1 = connector.selectQuery("select * from users " +
                    "where code  like '" + idCurrentUser + "'");
            ResultSet set2 = connector.selectQuery("select * from users " +
                    "where code  like '" + idFriend + "'");
            try {
                if (set1.next() && set2.next()) {
                    ResultSet resultSet = connector.selectQuery("select * from user_user " +
                            "where code_u1 like '" + idCurrentUser + "' and code_u2 " +
                            "like '" + idFriend + "'");

                    if (!resultSet.next()) {
                        connector.insertQuery("insert into user_user(code_u1, " +
                                "code_u2) value ( '" + idCurrentUser + "' , '" + idFriend + "' )");
                        connector.insertQuery("insert into user_user(code_u1, " +
                                "code_u2) value ( '" + idFriend + "' , '" + idCurrentUser + "' )");
                        return true;
                    }
                } else {
                    throw new UserNotFoundException();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            throw new SameUserException();
        }
        return false;
    }

    /**
     * gets all the friends that corresponds to the user with the id provided
     * in the parameters
     *
     * @param code the id of the user who made the request
     * @return an {@link ArrayList} of all the users that are friends with
     * the provided user
     *
     * */
    public synchronized ArrayList<User> getFriendsById(String code){


        ResultSet resultSet = connector.selectQuery("select * from user_user " +
                "where code_u1 like '" + code + "'");
        ArrayList<User> friends = new ArrayList<>();
        try {
            while (resultSet.next()) {
                friends.add(getUserById(resultSet.getString("code_u2")));
            }
        }catch (SQLException | UserNotFoundException e) {
            e.printStackTrace();
        }

        return friends;
    }


    /**
     * deletes a user from the database when provided the user's id
     *
     * @param code the id of the user to be deleted
     * @return a boolean indicating if the process was successful
     * @throws UserNotFoundException if the user's id was not found in the
     * database
     *
     * */
    public synchronized boolean deleteUserById(String code) throws UserNotFoundException {
        ResultSet resultSet = connector.selectQuery("select * from users " +
                "where code  like '" + code + "'");
        try {
            if(resultSet.next()){
                connector.deleteQuery("delete from user_user where code_u1 " +
                        "like '"+code+"' or code_u2 like '"+ code + "'");
                connector.deleteQuery("delete from users where code like '" + code+ "'");
                return true;
            }else {
                throw new UserNotFoundException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    /**
     * disconnects the UserDAO from the database
     *
     * @see ConnectorDB
     * */
    public synchronized void disconnect(){
        connector.disconnect();
        connected = false;
    }


}
