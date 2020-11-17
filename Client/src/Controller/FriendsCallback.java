package Controller;

import model.User;

import java.util.ArrayList;


/**
 * this interface is used as an observer to notify whoever implements it
 * about the changes that has happened on the friends list of a specific user
 */
public interface FriendsCallback {
    /**
     * this method is called when a change happens on the friends list of a
     * specific user
     * @param friends the new list of friends, after the update.
     */
    void onFriendsUpdated(ArrayList<User> friends);
}
