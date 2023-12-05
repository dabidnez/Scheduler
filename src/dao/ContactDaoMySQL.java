package dao;

import helper.QueryMySQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.ResultSet;

public class ContactDaoMySQL implements ContactDao {
    @Override
    public Contact getContactByID(int id) {
        try {
            ResultSet results = QueryMySQL.query("select * from contacts where Contact_ID=" + id + ";");
            results.next();
            return new Contact(results.getInt(1),
                    results.getString(2),
                    results.getString(3));
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public ObservableList<Contact> getAllContacts() {
        try {
            ObservableList<Contact> allContacts = FXCollections.observableArrayList();
            ResultSet results = QueryMySQL.query("select * from contacts;");
            while (results.next()) {
                allContacts.add(new Contact(results.getInt(1),
                        results.getString(2),
                        results.getString(3)));
            }
            return allContacts;
        } catch (Exception e) {
            System.out.println("--getAllContacts() Exception--");
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public ObservableList<String> getAllContactNames() {
        try {
            ObservableList<String> allContactNames = FXCollections.observableArrayList();
            ResultSet results = QueryMySQL.query("select Contact_Name from contacts;");
            while (results.next()) {
                allContactNames.add(results.getString(1));
            }
            return allContactNames;
        } catch (Exception e) {
            System.out.println("--getAllContactNames() Exception--");
            System.out.println(e.getMessage());
            return null;
        }
    }
}
