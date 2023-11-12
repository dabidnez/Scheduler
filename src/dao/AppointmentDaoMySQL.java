package dao;

import helper.QueryMySQL;
import helper.TimeZoneConversions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.ResultSet;


import static helper.QueryMySQL.query;
import static helper.QueryMySQL.queryUpdate;

public class AppointmentDaoMySQL implements AppointmentDao {
    @Override
    public ObservableList<Appointment> getAllAppointments() {
        try {
            ResultSet results = query("select * from appointments;");

            ObservableList<Appointment> appointments = FXCollections.observableArrayList();
            while (results.next()) {
                Appointment appointment = new Appointment(results.getInt(1),
                        results.getString(2),
                        results.getString(3),
                        results.getString(4),
                        results.getString(5),
                        TimeZoneConversions.timestampToZonedDateTime(results.getTimestamp(6)),
                        TimeZoneConversions.timestampToZonedDateTime(results.getTimestamp(7)),
                        TimeZoneConversions.timestampToZonedDateTime(results.getTimestamp(8)),
                        results.getString(9),
                        //this can definitely be optimized by rewriting without helper functions or other DAOs
                        new CustomerDaoMySQL().getCustomerByID(results.getInt(10)),
                        new UserDaoMySQL().getUserByID(results.getInt(11)),
                        new ContactDaoMySQL().getContactByID(results.getInt(12)));
                appointments.add(appointment);
            }
            return appointments;

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteAssociatedAppointments(int customer_id) {
        //vulnerable to sql injection
        queryUpdate("delete from appointments where customer_id=" + customer_id + ";");
    }
}
