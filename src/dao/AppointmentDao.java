package dao;

import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.SQLException;
import java.time.ZonedDateTime;

/**
 * Appointment Related Queries
 */
public interface AppointmentDao {
    /**
     * Return all appointments in the database.
     * @return
     */
    public ObservableList<Appointment> getAllAppointments();

    /**
     * Delete appointments with the given customer id.
     * @param customer_id
     */
    public void deleteAssociatedAppointments(int customer_id);
    public void addAppointment(String title,
                               String description,
                               String location,
                               String type,
                               ZonedDateTime start_date_time,
                               ZonedDateTime end_date_time,
                               String username,
                               int customer_id,
                               int user_id,
                               int contact_id);

    /**
     * Update appointment based on the following input.
     * @param id
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start_date_time
     * @param end_date_time
     * @param username
     * @param customerIdByName
     * @param usernameIdByName
     * @param contactIdByName
     */
    void updateAppointment(int id, String title, String description, String location, String type, ZonedDateTime start_date_time, ZonedDateTime end_date_time, String username, int customerIdByName, int usernameIdByName, int contactIdByName);

    /**
     * Delete an appointment by its ID.
     * @param appointment_id
     */
    void deleteAppointment(int appointment_id);

    /**
     * Get all appointments from the current month.
     * @return
     */
    ObservableList<Appointment> getAllAppointmentsThisMonth();

    /**
     * Get all appointments from the current week, starts on Sunday.
     * @return
     */
    ObservableList<Appointment> getAllAppointmentsThisWeek();

    /**
     * Return a list of all type names.
     * @return
     */
    ObservableList<String> getAllTypes();

    /**
     * Returns a list of all years used for appointments.
     * @return
     */
    ObservableList<String> getAllYears();

    /**
     * Returns a list of all months used for appointments.
     * @return
     */
    ObservableList<String> getAllMonths();

    /**
     * Get number of appointments based on month, year, and type filters.
     * @param month
     * @param year
     * @param type
     * @return
     * @throws SQLException
     */
    int getCountOfFilteredAppointments(String month, String year, String type) throws SQLException;

    /**
     * Get all appointments in the future associated with given contact.
     * @param contact
     * @return
     * @throws SQLException
     */
    ObservableList<Appointment> getFutureAppointmentsByContact(String contact) throws SQLException;

    /**
     * Get a list of all the names of locations used in appointments.
     * @return
     * @throws SQLException
     */
    ObservableList<String> getAllLocationNames() throws SQLException;

    /**
     * Get all future appointments given a certain location.
     * @param location
     * @return
     * @throws SQLException
     */
    ObservableList<Appointment> getFutureAppointmentsByLocation(String location) throws SQLException;
}
