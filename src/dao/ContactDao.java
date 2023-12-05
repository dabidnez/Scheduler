package dao;

import javafx.collections.ObservableList;
import model.Contact;

public interface ContactDao {
    /**
     * Return a contact object given its id.
     * @param id
     * @return
     */
    Contact getContactByID(int id);

    /**
     * Get all contacts stored in the database.
     * @return
     */
    ObservableList<Contact> getAllContacts();

    /**
     * Get all contact names stored in the database.
     * @return
     */
    ObservableList<String> getAllContactNames();
}
