package dao;

import model.Contact;

public interface ContactDao {
    Contact getContactByID(int id);
}
