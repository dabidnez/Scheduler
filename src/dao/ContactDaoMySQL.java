package dao;

import helper.QueryMySQL;
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
}
