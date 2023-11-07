package dao;

import javafx.collections.ObservableList;
import model.Customer;

import java.util.ArrayList;

public interface CustomerDao {
    ObservableList<Customer> getAllCustomers();
    Customer getCustomerByID(int id);
}
