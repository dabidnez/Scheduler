package dao;

import model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoMySQLTest {

    @Test
    void getUser() {
        UserDaoMySQL testUser = new UserDaoMySQL();
        User testuser = testUser.getUser("test");
        User adminuser = testUser.getUser("admin");
        User erroruser = testUser.getUser("error");
        assertTrue(testuser.getPassword().equals("test"));
        assertTrue(adminuser.getPassword().equals("admin"));
        assertTrue(erroruser == null);
    }
}