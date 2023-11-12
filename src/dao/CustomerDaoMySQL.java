package dao;

import helper.JDBC;
import helper.QueryMySQL;
import helper.TimeZoneConversions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.FirstLevelDivision;

import javax.management.Query;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.*;
import java.util.TimeZone;
import java.util.Calendar;

import static helper.QueryMySQL.query;
import static helper.QueryMySQL.queryUpdate;

public class CustomerDaoMySQL implements CustomerDao {
    @Override
    public ObservableList<Customer> getAllCustomers() {
        try {
            ResultSet results = query("select * from customers;");
            ObservableList<Customer> customers = FXCollections.observableArrayList();
            while (results.next()) {
                Customer current_customer = new Customer();
                current_customer.setCustomer_id(results.getInt(1));
                current_customer.setCustomer_name(results.getString(2));
                current_customer.setAddress(results.getString(3));
                current_customer.setPostal_code(results.getString(4));
                current_customer.setPhone(results.getString(5));

                LocalDate create_date = results.getDate(6).toLocalDate();
                LocalTime create_time = results.getTime(6).toLocalTime();
                LocalDateTime create_date_time = LocalDateTime.of(create_date, create_time);
                ZonedDateTime create_date_time_zoned = ZonedDateTime.of(create_date_time, ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                current_customer.setCreate_date(create_date_time_zoned);

                current_customer.setCreated_by(results.getString(7));

                LocalDate update_date = results.getDate(8).toLocalDate();
                LocalTime update_time = results.getTime(8).toLocalTime();
                LocalDateTime update_date_time = LocalDateTime.of(update_date, update_time);
                ZonedDateTime update_date_time_zoned = ZonedDateTime.of(update_date_time, ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                current_customer.setLast_update(update_date_time_zoned);

                current_customer.setLast_updated_by(results.getString(9));
                current_customer.setDivision((new DivisionDaoMySQL()).getFirstLevelDivisionByID(results.getInt(10)));
                customers.add(current_customer);
            }
            return customers;
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    @Override
    public Customer getCustomerByID(int id) {
        try {
            ResultSet results = query("select * from customers where Customer_ID=" + id + ";");
            results.next();
            return new Customer(results.getInt(1),
                    results.getString(2),
                    results.getString(3),
                    results.getString(4),
                    results.getString(5),
                    TimeZoneConversions.timestampToZonedDateTime(results.getTimestamp(6)),
                    results.getString(7),
                    TimeZoneConversions.timestampToZonedDateTime(results.getTimestamp(8)),
                    results.getString(9),
                    new DivisionDaoMySQL().getFirstLevelDivisionByID(results.getInt(10)));

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public void insertCustomer(Customer customer) {
        try {
            queryUpdate("insert into customers " +
                    "(Customer_name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID)" +
                    " values (\"" +
                    customer.getCustomer_name() +
                    "\", \"" +
                    customer.getAddress() +
                    "\", \"" +
                    customer.getPostal_code() +
                    "\", \"" +
                    customer.getPhone() +
                    "\", now(), \"" +
                    customer.getCreated_by() +
                    "\", now(), \"" +
                    customer.getLast_updated_by() +
                    "\", " +
                    customer.getDivision().getDivision_id() +
                    ");");


        } catch (Exception e) {
            System.out.println("--Insert customer failed!--");
            System.out.println(e);
        }
    }

    @Override
    public void deleteCustomer(int customer_id) {
        queryUpdate("delete from customers where customer_id=" + customer_id + ";");
    }
}
