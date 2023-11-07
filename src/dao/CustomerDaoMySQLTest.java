package dao;

import model.Customer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerDaoMySQLTest {
    @Test
    void getAllCustomers() {
        CustomerDao dao = new CustomerDaoMySQL();
        List<Customer> allCustomers = dao.getAllCustomers();
        assertTrue(allCustomers.get(0).getCustomer_name().equals("Daddy Warbucks"));
    }

    @Test
    void getCustomerByID() {
        CustomerDao dao = new CustomerDaoMySQL();
        assertTrue(dao.getCustomerByID(1).getCustomer_name().equals("Daddy Warbucks"));
    }
}
