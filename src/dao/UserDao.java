package dao;

import javafx.collections.ObservableList;
import model.User;

/**
 * User object related queries.
 */
public interface UserDao {
    /**
     * Return a user object based on its username.
     * @param username
     * @return
     */
    User getUser(String username);

    /**
     * Return a user object based on its id.
     * @param id
     * @return
     */
    User getUserByID(int id);

    /**
     * Return a list of all users.
     * @return
     */
    ObservableList<User> getAllUsers();
}
