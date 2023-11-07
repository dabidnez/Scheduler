package dao;

import model.User;

public interface UserDao {
    User getUser(String username);
    User getUserByID(int id);
}
