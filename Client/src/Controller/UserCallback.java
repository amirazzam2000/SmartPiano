package Controller;

/**
 * this interface is used as an observer to notify whoever implements it
 * about the changes that has happened on the currently logged in user
 */
public interface UserCallback {
    /**
     * this method is called when the currently logged in user have been
     * deleted
     */
    void onUserDeleted();
}
