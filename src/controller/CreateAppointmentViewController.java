package controller;

import dao.AppointmentDaoMySQL;
import dao.ContactDaoMySQL;
import dao.CustomerDaoMySQL;
import dao.UserDaoMySQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import javax.xml.stream.Location;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Time;
import java.time.*;
import java.util.Locale;
import java.util.Objects;
import java.util.HashMap;

/**
 * Controller for create appointment view form UI.
 */
public class CreateAppointmentViewController {

    @FXML
    private ComboBox<String> ContactDropDown;

    @FXML
    private Text ContactNameErrorTxt;

    @FXML
    private ComboBox<String> CustomerDropDown;

    @FXML
    private Text CustomerNameErrorTxt;

    @FXML
    private Text UsernameErrorTxt;

    @FXML
    private Text DescriptionErrorTxt;

    @FXML
    private TextField DescriptionTxt;

    @FXML
    private Text StartYearErrorTxt;

    @FXML
    private Text StartMonthErrorTxt;

    @FXML
    private Text StartDayErrorTxt;

    @FXML
    private Text StartHourErrorTxt;

    @FXML
    private Text StartMinuteErrorTxt;

    @FXML
    private Text EndHourErrorTxt;

    @FXML
    private TextField EndHourTxt;

    @FXML
    private Text EndMinuteErrorTxt;

    @FXML
    private TextField EndMinuteTxt;

    @FXML
    private Text LocationErrorTxt;

    @FXML
    private TextField LocationTxt;

    @FXML
    private TextField StartDayTxt;

    @FXML
    private TextField StartHourTxt;

    @FXML
    private TextField StartMinuteTxt;

    @FXML
    private TextField StartMonthTxt;

    @FXML
    private TextField StartYearTxt;

    @FXML
    private Text TitleErrorTxt;

    @FXML
    private TextField TitleTxt;

    @FXML
    private Text TypeErrorTxt;

    @FXML
    private TextField TypeTxt;

    @FXML
    private Text DateTimeErrorTxt;

    @FXML
    private Text TimeError;

    @FXML
    private ComboBox<String> UsernameDropDown;

    @FXML
    private TextField IdTxt;

    @FXML
    private Text BusinessHoursTxt;

    private String username;

    private boolean update;

    void sendUsername(String username) { this.username = username; }

    private ObservableList<Contact> contacts;

    private ObservableList<Customer> customers;

    private ObservableList<User> users;

    private ObservableList<Appointment> appointments;

    private Appointment update_appointment;

    /**
     * Resets error messages before another round of input validation.
     */
    private void resetErrors() {
        TitleErrorTxt.setText("");
        DescriptionErrorTxt.setText("");
        LocationErrorTxt.setText("");
        TypeErrorTxt.setText("");
        StartYearErrorTxt.setText("");
        StartMonthErrorTxt.setText("");
        StartDayErrorTxt.setText("");
        StartMinuteErrorTxt.setText("");
        StartHourErrorTxt.setText("");
        EndHourErrorTxt.setText("");
        EndMinuteErrorTxt.setText("");
        DateTimeErrorTxt.setText("");
        ContactNameErrorTxt.setText("");
        UsernameErrorTxt.setText("");
        CustomerNameErrorTxt.setText("");
        TimeError.setText("");
    }

    /**
     * for every appointment, if new app start time before app start time and new app end time after app start time
     *                                or if new app start time before app end time and new app end time after app end time
     *                                or if new app start time after app start time and new app end time before app end time
     *                                return true
     *
     *            after checking each appointment, return false
     * @param start_date_time
     * @param end_date_time
     * @return
     */
    private boolean appointmentHasConflicts(ZonedDateTime start_date_time, ZonedDateTime end_date_time) {
        for (Appointment appointment: appointments) {
            if (start_date_time.isBefore(appointment.getStart()) && end_date_time.isAfter(appointment.getStart()))
                return true;
            if (start_date_time.isBefore(appointment.getEnd()) && end_date_time.isAfter(appointment.getEnd()))
                return true;
            if (start_date_time.isAfter(appointment.getStart()) && end_date_time.isBefore(appointment.getEnd()))
                return true;
            if (start_date_time.isEqual(appointment.getStart()) && end_date_time.isEqual(appointment.getEnd()))
                return true;
        }

        return false;
    }

    /**
     * same as appointmentHasConflicts() except checks to see if compared appointment is the same as appointment to be updated
     *          and excludes that appointment from comparison
     * @param start_date_time
     * @param end_date_time
     * @param appointment_id
     * @return
     */
    private boolean appointmentUpdateHasConflicts(ZonedDateTime start_date_time, ZonedDateTime end_date_time, int appointment_id) {
        for (Appointment appointment: appointments) {
            if (appointment.getAppointment_id() != appointment_id) {
                if (start_date_time.isBefore(appointment.getStart()) && end_date_time.isAfter(appointment.getStart()))
                    return true;
                if (start_date_time.isBefore(appointment.getEnd()) && end_date_time.isAfter(appointment.getEnd()))
                    return true;
                if (start_date_time.isAfter(appointment.getStart()) && end_date_time.isBefore(appointment.getEnd()))
                    return true;
                if (start_date_time.isEqual(appointment.getStart()) && end_date_time.isEqual(appointment.getEnd()))
                    return true;
            }
        }

        return false;
    }

    //could use a map instead of the below functions
    private int getCustomerIdByName(String customer_name) {
        for (Customer customer: customers) {
            if (customer.getCustomer_name().equals(customer_name))
                return customer.getCustomer_id();
        }
        return -1;
    }

    private int getUsernameIdByName(String username) {
        for (User user: users) {
            if (user.getUser_name().equals(username))
                return user.getUser_id();
        }
        return -1;
    }

    private int getContactIdByName(String contact_name) {
        for (Contact contact : contacts) {
            if (contact.getContact_name().equals(contact_name))
                return contact.getContact_id();
        }
        return -1;
    }

    private String getContactNameById(int contact_id) {
        for (Contact contact: contacts) {
            if (contact.getContact_id() == contact_id)
                return contact.getContact_name();
        }
        return null;
    }

    private String getUsernameById(int user_id) {
        for (User user: users) {
            if (user.getUser_id() == user_id) {
                return user.getUser_name();
            }
        }
        return null;
    }

    private String getCustomerById(int customer_id) {
        for (Customer customer: customers) {
            if (customer.getCustomer_id() == customer_id)
                return customer.getCustomer_name();
        }
        return null;
    }

    /**
     * Handles sending data of selected appointment if doing an update.
     * @param appointment
     */
    void sendUpdateAppointment(Appointment appointment) {
        this.update_appointment = appointment;
        this.update = true;
        IdTxt.setText(Integer.toString(update_appointment.getAppointment_id()));
        TitleTxt.setText(update_appointment.getTitle());
        DescriptionTxt.setText(update_appointment.getDescription());
        LocationTxt.setText(update_appointment.getLocation());
        TypeTxt.setText(update_appointment.getType());
        StartHourTxt.setText(Integer.toString(update_appointment.getStart().getHour()));
        StartMinuteTxt.setText(Integer.toString(update_appointment.getStart().getMinute()));
        StartMonthTxt.setText(Integer.toString(update_appointment.getStart().getMonth().getValue()));
        StartDayTxt.setText(Integer.toString(update_appointment.getStart().getDayOfMonth()));
        StartYearTxt.setText(Integer.toString(update_appointment.getStart().getYear()));
        EndHourTxt.setText(Integer.toString(update_appointment.getEnd().getHour()));
        EndMinuteTxt.setText(Integer.toString(update_appointment.getStart().getHour()));
        CustomerDropDown.setValue(getCustomerById(update_appointment.getCustomer_id()));
        UsernameDropDown.setValue(getUsernameById(update_appointment.getUser_id()));
        ContactDropDown.setValue(getContactNameById(update_appointment.getContact_id()));
    }

    /**
     * Handles add button on the form.
     * First, validate all inputs
     * Then, if this form was created with the update button, use set ID to update an existing appointment.
     * Otherwise, create new form with valid input.
     * @param event
     * @throws IOException
     */
    @FXML
    void onClickAdd(ActionEvent event) throws IOException {
        //if update populate with existing data
        //check that string fields are not blank
        //check if appointment time is valid



        resetErrors();

        boolean error = false;
        boolean time_error = false;

        String title = TitleTxt.getText();
        if (title.isBlank()) {
            TitleErrorTxt.setText("Enter a valid title");
            error = true;
        }
        String description = DescriptionTxt.getText();
        if (description.isBlank()) {
            DescriptionErrorTxt.setText("Enter a valid description");
            error = true;
        }
        String location = LocationTxt.getText();
        if (location.isBlank()) {
            LocationErrorTxt.setText("Enter a valid location");
            error = true;
        }
        String type = TypeTxt.getText();
        if (type.isBlank()) {
            TypeErrorTxt.setText("Enter a valid type");
            error = true;
        }
        String contact_name = ContactDropDown.getValue();
        if (contact_name == null) {
            ContactNameErrorTxt.setText("Please select a contact");
            error = true;
        }
        String customer_name = CustomerDropDown.getValue();
        if (customer_name == null) {
            CustomerNameErrorTxt.setText("Please select a customer");
            error = true;
        }
        int customer_id = getCustomerIdByName(customer_name);

        String username = UsernameDropDown.getValue();
        if (username == null) {
            UsernameErrorTxt.setText("Please select a username");
            error = true;
        }

        ZonedDateTime currentTime = ZonedDateTime.now();

        int start_year = -1;
        int start_month = -1;
        int start_day = -1;
        int start_hour = -1;
        int start_minute = -1;
        int end_hour = -1;
        int end_minute = -1;
        try {
            start_year = Integer.parseInt(StartYearTxt.getText());
        } catch (NumberFormatException e) {
            StartYearErrorTxt.setText("Enter a valid start year");
            error = true;
            time_error = true;
        }
        try {
            start_month = Integer.parseInt(StartMonthTxt.getText());
        } catch (NumberFormatException e) {
            StartMonthErrorTxt.setText("Enter a valid start month");
            error = true;
            time_error = true;
        }
        try {
            start_day = Integer.parseInt(StartDayTxt.getText());
        } catch (NumberFormatException e) {
            StartDayErrorTxt.setText("Enter a valid start day");
            error = true;
            time_error = true;
        }
        try {
            start_hour = Integer.parseInt(StartHourTxt.getText());
        } catch (NumberFormatException e) {
            StartHourErrorTxt.setText("Enter a valid start hour");
            error = true;
            time_error = true;
        }
        try {
            start_minute = Integer.parseInt(StartMinuteTxt.getText());
        } catch (NumberFormatException e) {
            StartMinuteErrorTxt.setText("Enter a valid start minute");
            error = true;
            time_error = true;
        }
        try {
            end_hour = Integer.parseInt(EndHourTxt.getText());
        } catch (NumberFormatException e) {
            EndHourErrorTxt.setText("Enter a valid end hour");
            error = true;
            time_error = true;
        }
        try {
            end_minute = Integer.parseInt(EndMinuteTxt.getText());
        } catch (NumberFormatException e) {
            EndMinuteErrorTxt.setText("Enter a valid end minute");
            error = true;
            time_error = true;
        }

        //if appointment time is not a valid date set error
        //if appointment time end is before start
        //if appointment time is not in the future set error to enter a time in the future
        //if appointment time overlaps set error to true and overlap message
        //if out of business hours set error to true and out of business hours message
        ZonedDateTime start_date_time = ZonedDateTime.of(0,1,1,0,0,0,0, ZoneId.systemDefault());
        ZonedDateTime end_date_time = ZonedDateTime.of(0,1,1,0,0,0,0, ZoneId.systemDefault());
        try {
            start_date_time = ZonedDateTime.of(
                    start_year,
                    start_month,
                    start_day,
                    start_hour,
                    start_minute,
                    0,
                    0,
                    ZoneId.systemDefault());
            end_date_time = ZonedDateTime.of(
                    start_year,
                    start_month,
                    start_day,
                    end_hour,
                    end_minute,
                    0,
                    0,
                    ZoneId.systemDefault());
        } catch (DateTimeException e) {
            error = true;
            time_error = true;
            DateTimeErrorTxt.setText(e.getMessage());
        }

        if (!time_error) {
            if (end_date_time.isBefore(start_date_time)) {
                error = true;
                time_error = true;
                TimeError.setText("End time is before start time!");
            }
        }

        if (!time_error) {
            if (start_date_time.isBefore(ZonedDateTime.now())) {
                error = true;
                time_error = true;
                TimeError.setText("Time is not set in the future");
            }
        }

        if(!time_error) {
            if (start_date_time.equals(end_date_time)) {
                error = true;
                time_error = true;
                TimeError.setText("Appointment is 0 minutes long!");
            }
        }

        if (!time_error) {
            //create zoned date time of request appointment time
            //convert it to est
            //grab date from above object and use it to create a new object of the business hours
            //return comparison
            ZonedDateTime start_date_time_est = start_date_time.withZoneSameInstant(ZoneId.of("America/New_York"));
            ZonedDateTime end_date_time_est = end_date_time.withZoneSameInstant(ZoneId.of("America/New_York"));
            ZonedDateTime start_of_business_hours = ZonedDateTime.of(start_year, start_month, start_day, 7, 59  , 59, 0, ZoneId.of("America/New_York"));
            ZonedDateTime start_of_business_hours_previous_day = start_of_business_hours.minusDays(1);
            ZonedDateTime start_of_business_hours_next_day = start_of_business_hours.plusDays(1);
            ZonedDateTime end_of_business_hours = ZonedDateTime.of(start_year, start_month, start_day, 22, 0, 1, 0, ZoneId.of("America/New_York"));
            ZonedDateTime end_of_business_hours_previous_day = end_of_business_hours.minusDays(1);
            ZonedDateTime end_of_business_hours_next_day = end_of_business_hours.plusDays(1);

            if (!((start_date_time_est.isAfter(start_of_business_hours) && end_date_time_est.isBefore(end_of_business_hours)) ||
                  (start_date_time_est.isAfter(start_of_business_hours_previous_day) && end_date_time_est.isBefore(end_of_business_hours_previous_day)) ||
                  (start_date_time_est.isAfter(start_of_business_hours_next_day) && start_date_time_est.isBefore(end_of_business_hours_next_day)))) {
                time_error = true;
                error = true;
                TimeError.setText("Appointment is set outside of business hours.");
            }
        }

        if (!time_error) {
            if (update) {
                if (appointmentUpdateHasConflicts(start_date_time, end_date_time, Integer.parseInt(IdTxt.getText()))) {
                    error = true;
                    time_error = true;
                    TimeError.setText("Appointment time conflicts with another appointment!");
                }
            } else {
                if (appointmentHasConflicts(start_date_time, end_date_time)) {
                    error = true;
                    time_error = true;
                    TimeError.setText("Appointment time conflicts with another appointment!");
                }
            }
        }

        if (!error) {
            if (update) {
                new AppointmentDaoMySQL().updateAppointment(
                        Integer.parseInt(IdTxt.getText()),
                        title,
                        description,
                        location,
                        type,
                        start_date_time,
                        end_date_time,
                        username,
                        getCustomerIdByName(customer_name),
                        getUsernameIdByName(username),
                        getContactIdByName(contact_name)
                );
            } else {
                (new AppointmentDaoMySQL()).addAppointment(
                        title,
                        description,
                        location,
                        type,
                        start_date_time,
                        end_date_time,
                        username,
                        getCustomerIdByName(customer_name),
                        getUsernameIdByName(username),
                        getContactIdByName(contact_name)

                );
            }
            onClickCancel(event);
        }
    }

    /**
     * Handles clicking the cancel button, returns window to main menu.
     * @param event
     * @throws IOException
     */
    @FXML
    void onClickCancel(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/main-view.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Initalize the view. Get all contacts, customers, users, and appointments for the purposes of filling out the form and validating input.
     * Uses lambda function to transform object list to a list of strings, this is more readable, filter programming paradigm.
     */
    @FXML
    void initialize() {
        contacts = new ContactDaoMySQL().getAllContacts();
        customers = new CustomerDaoMySQL().getAllCustomers();
        users = new UserDaoMySQL().getAllUsers();
        appointments = new AppointmentDaoMySQL().getAllAppointments();

        ObservableList<String> contactNames = FXCollections.observableArrayList();
        ObservableList<String> customerNames = FXCollections.observableArrayList();
        ObservableList<String> usernames = FXCollections.observableArrayList();

        // Lambda function, simplifies loop to 1 line for readability and simplicity, a sort of filter.
        contacts.forEach(contact -> contactNames.add(contact.getContact_name()));
        // Lambda function, simplifies loop to 1 line for readability and simplicity, a sort of filter.
        customers.forEach(customer -> customerNames.add(customer.getCustomer_name()));
        // Lambda function, simplifies loop to 1 line for readability and simplicity, a sort of filter.
        users.forEach(user -> usernames.add(user.getUser_name()));

        ContactDropDown.setItems(contactNames);
        CustomerDropDown.setItems(customerNames);
        UsernameDropDown.setItems(usernames);

        ZonedDateTime today = ZonedDateTime.now();
        ZonedDateTime start_of_business_hours = ZonedDateTime.of(today.getYear(), today.getMonthValue(), today.getDayOfMonth(), 8, 0  , 0, 0, ZoneId.of("America/New_York"));
        ZonedDateTime end_of_business_hours = ZonedDateTime.of(today.getYear(), today.getMonthValue(), today.getDayOfMonth(), 22, 0  , 0, 0, ZoneId.of("America/New_York"));
        ZonedDateTime local_start_of_business_hours = start_of_business_hours.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime local_end_of_business_hours = end_of_business_hours.withZoneSameInstant(ZoneId.systemDefault());

        int local_start_hour = local_start_of_business_hours.getHour();
        int local_start_minute = local_start_of_business_hours.getMinute();

        int local_end_hour = local_end_of_business_hours.getHour();
        int local_end_minute = local_end_of_business_hours.getMinute();

        BusinessHoursTxt.setText(String.format("Remember, business hours are 8AM to 10PM every day EST, which for today is %s to %s.",
                local_start_of_business_hours,
                local_end_of_business_hours));

    }

}

