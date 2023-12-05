package dao;

import javafx.collections.ObservableList;
import model.Customer;
import model.FirstLevelDivision;

import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * Customer object related queries.
 */
public interface CustomerDao {
    /**
     * Return a list of all customers.
     * @return
     */
    ObservableList<Customer> getAllCustomers();

    /**
     * Get a customer object based on its id.
     * @param id
     * @return
     */
    Customer getCustomerByID(int id);

    /**
     * Insert a customer into the database given a customer object.
     * @param customer
     */
    void insertCustomer(Customer customer);

    /**
     * Deletes a customer given a customer id.
     * @param customer_id
     */
    void deleteCustomer(int customer_id);

    /**
     * Update a customer given the following parameters.
     * @param customer_id
     * @param name
     * @param address
     * @param postal
     * @param phone
     * @param update_user
     * @param division_id
     */
    void updateCustomer(int customer_id, String name, String address, String postal, String phone, String update_user, int division_id);
}
