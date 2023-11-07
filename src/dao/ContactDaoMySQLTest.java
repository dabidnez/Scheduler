
package dao;

import model.Contact;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ContactDaoMySQLTest {
    @Test
    void getContactByID() {
        ContactDao dao = new ContactDaoMySQL();
        assertTrue(dao.getContactByID(1).getEmail().equals("acoasta@company.com"));
    }

}