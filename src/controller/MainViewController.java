
package controller;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZonedDateTime;

/**
 * Controller for the main page view.
 */
public class MainViewController {
    @FXML
    private TableView<Appointment> appointmentsTable;
    
    @FXML
    private TableColumn<Appointment, String> appointmentsContactIdCollumn;

    @FXML
    private TableColumn<Appointment, String> appointmentsCustomerIdCollumn;

    @FXML
    private TableColumn<Appointment, String> appointmentsUserIdCollumn;

    @FXML
    private TableColumn<Appointment, String> appointmentsDescriptionCollumn;

    @FXML
    private TableColumn<Appointment, String> appointmentsEndTimeCollumn;

    @FXML
    private TableColumn<Appointment, String> appointmentsIDCollumn;

    @FXML
    private TableColumn<Appointment, String> appointmentsLocationCollumn;

    @FXML
    private TableColumn<Appointment, String> appointmentsStartTimeCollumn;

    @FXML
    private TableColumn<Appointment, String> appointmentsTitleCollumn;

    @FXML
    private TableColumn<Appointment, String> appointmentsTypeCollumn;


    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<Customer, String> customerAddressCollumn;

    @FXML
    private TableColumn<Customer, String> customerCreateDateCollumn;

    @FXML
    private TableColumn<Customer, String> customerCreatedByCollumn;

    @FXML
    private TableColumn<Customer, String> customerDivisionCollumn;

    @FXML
    private TableColumn<Customer, Integer> customerIDCollumn;

    @FXML
    private TableColumn<Customer, String> customerLastUpdatedByCollumn;

    @FXML
    private TableColumn<Customer, String> customerLastUpdatedCollumn;

    @FXML
    private TableColumn<Customer, String> customerNameCollumn;

    @FXML
    private TableColumn<Customer, String> customerPhoneCollumn;

    @FXML
    private TableColumn<Customer, String> customerPostalCodeCollumn;


    @FXML
    private TableView<Appointment> ContactTable;

    @FXML
    private TableColumn<Appointment, String> ContactIdColumn;

    @FXML
    private TableColumn<Appointment, String> ContactTitleColumn;

    @FXML
    private TableColumn<Appointment, String> ContactDescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> ContactTypeColumn;

    @FXML
    private TableColumn<Appointment, String> ContactStartColumn;

    @FXML
    private TableColumn<Appointment, String> ContactEndColumn;

    @FXML
    private TableColumn<Appointment, String> ContactCustomerIdColumn;


    @FXML
    private TableView<Appointment> LocationTable;

    @FXML
    private TableColumn<Appointment, String> LocationIdColumn;

    @FXML
    private TableColumn<Appointment, String> LocationTitleColumn;

    @FXML
    private TableColumn<Appointment, String> LocationDescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> LocationTypeColumn;

    @FXML
    private TableColumn<Appointment, String> LocationStartColumn;

    @FXML
    private TableColumn<Appointment, String> LocationEndColumn;

    @FXML
    private TableColumn<Appointment, String> LocationCustomerIdColumn;


    @FXML
    private Label welcomeTxt;

    @FXML
    private Button DeleteCustomerButton;

    @FXML
    private Button DeleteAppointmentButton;

    @FXML
    private Text CustomerDeletedTxt;

    @FXML
    private Text AppointmentDeletedTxt;

    @FXML
    private Text UpcomingAppointmentsTxt;

    @FXML
    private Text NumberOfAppointments;

    private String username;

    @FXML
    private ComboBox<String> TypeDropDown;

    @FXML
    private ComboBox<String> MonthDropDown;

    @FXML
    private ComboBox<String> YearDropDown;

    @FXML
    private ComboBox<String> ContactDropDown;

    @FXML
    private ComboBox<String> LocationDropDown;

    @FXML
    private Text ContactTitleTxt;

    @FXML
    private Text LocationTitleTxt;

    /**
     * Handles sending username from login to the main view.
     * @param username
     */
    void sendUsername(String username) {
        this.username = username;
    }

    /**
     * Handles switching to create window.
     * @param event
     * @throws IOException
     */
    @FXML
    void onClickCreateCustomerButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/add-customer-view.fxml"));
        loader.load();

        CreateViewController createViewController = loader.getController();
        createViewController.sendUsername(username);
        Parent scene = loader.getRoot();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Handles deleting a selected customer with confirmation and deleting associated appointments, does nothing
     * if no customer is selected.
     * @param event
     */
    @FXML
    void onClickDeleteButton(ActionEvent event) {
        CustomerDao customerDao = new CustomerDaoMySQL();
        AppointmentDao appointmentDao = new AppointmentDaoMySQL();;
        if (DeleteCustomerButton.getText().equals("D E L E T E")) {
            DeleteCustomerButton.setText("Confirm? This will delete all associated appoointments.");
            return;
        }
        try {
            Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
            appointmentDao.deleteAssociatedAppointments(selectedCustomer.getCustomer_id());
            customerDao.deleteCustomer(selectedCustomer.getCustomer_id());
            CustomerDeletedTxt.setText("Customer Deleted");
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        DeleteCustomerButton.setText("D E L E T E");
        customersTable.setItems(customerDao.getAllCustomers());
        appointmentsTable.setItems(appointmentDao.getAllAppointments());
    }

    /**
     * Opens up the create window but prepopulates the data with selected customer. Does nothing if no customer selected.
     * @param event
     * @throws IOException
     */
    @FXML
    void onClickUpdateButton(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/add-customer-view.fxml"));
            loader.load();

            CreateViewController createViewController = loader.getController();
            createViewController.sendUsername(username);
            createViewController.sendUpdateInfo(customersTable.getSelectionModel().getSelectedItem());
            Parent scene = loader.getRoot();

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (Exception e) {
            System.out.println("--onClickUpdateButton()--");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handles moving to create window for appointments.
     * @param event
     * @throws IOException
     */
    @FXML
    void onClickCreateAppointment(ActionEvent event) throws IOException {
        System.out.println("click create appointment");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/add-appointment-view.fxml"));
        loader.load();

        CreateAppointmentViewController createAppointmentViewController = loader.getController();
        createAppointmentViewController.sendUsername(username);
        Parent scene = loader.getRoot();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Handles deleting an appointment with confirmation. Does nothing if no appointment is selected.
     * @param event
     */
    @FXML
    void onClickDeleteAppointment(ActionEvent event) {
        System.out.println("click delete appointment");
        AppointmentDao appointmentDao = new AppointmentDaoMySQL();

        if (DeleteAppointmentButton.getText().equals("D E L E T E")) {
            DeleteAppointmentButton.setText("Confirm?");
            return;
        }
        try {
            Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
            appointmentDao.deleteAppointment(selectedAppointment.getAppointment_id());
            int id = selectedAppointment.getAppointment_id();
            String type = selectedAppointment.getType();
            AppointmentDeletedTxt.setText("Appointment id: " + Integer.toString(id) + ", type: " + type + ", has been cancelled");
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        DeleteAppointmentButton.setText("D E L E T E");
        appointmentsTable.setItems(appointmentDao.getAllAppointments());
    }


    /**
     * Opens create appointment window but prepoulates form with selected appointment, does nothing if no appointment
     * is selected.
     * @param event
     */
    @FXML
    void onClickUpdateAppointment(ActionEvent event) {
        try {
            Appointment appointment_to_update = appointmentsTable.getSelectionModel().getSelectedItem();
            int appointment_id = appointment_to_update.getAppointment_id();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/add-appointment-view.fxml"));
            loader.load();

            CreateAppointmentViewController createAppointmentViewController = loader.getController();
            createAppointmentViewController.sendUsername(username);
            createAppointmentViewController.sendUpdateAppointment(appointment_to_update);
            Parent scene = loader.getRoot();

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (Exception e) {
            System.out.println("--onClickUpdateButton()--");
            System.out.println(e);
            System.out.println(e.getCause());
        }
    }

    /**
     * Shows appointments for this month only on selecting the month radio button.
     * @param action
     */
    @FXML
    void onSelectMonth(ActionEvent action) {
        appointmentsTable.setItems((new AppointmentDaoMySQL()).getAllAppointmentsThisMonth());
    }

    /**
     * Shows appointments only in this calendar week, which start on Sundays, on selection of week radio button.
     * @param action
     */
    @FXML
    void onSelectWeek(ActionEvent action) {
        appointmentsTable.setItems((new AppointmentDaoMySQL()).getAllAppointmentsThisWeek());
    }

    /**
     * Shows all appointments on selection of all time radio button. This is the default view.
     * @param action
     */
    @FXML
    void onSelectAllTime(ActionEvent action) {
        appointmentsTable.setItems((new AppointmentDaoMySQL()).getAllAppointments());
    }

    /**
     * Shows future appointments associated with selected contact.
     * @param action
     * @throws SQLException
     */
    @FXML
    void onClickContactDropDown(ActionEvent action) throws SQLException {
        AppointmentDao appointmentDao = new AppointmentDaoMySQL();
        ObservableList<Appointment> appointments_by_contact = appointmentDao.getFutureAppointmentsByContact(ContactDropDown.getValue());
        ContactTable.setItems(appointments_by_contact);
        ContactTitleTxt.setText(ContactDropDown.getValue() + "'s Schedule.");
    }

    /**
     * Returns all appointments that will occur in the next 15 minutes.
     * @param appointments
     * @return
     */
    private ObservableList<Appointment> getAppointmentsInNext15Minutes(ObservableList<Appointment> appointments) {
        ObservableList<Appointment> appointments_in_next_15_minutes = FXCollections.observableArrayList();
        for (Appointment appointment: appointments) {
            System.out.println("her1");
            ZonedDateTime time_in_15 = ZonedDateTime.now().plusMinutes(16);
            ZonedDateTime start_of_appointment = appointment.getStart();
            boolean appointment_is_in_15 = start_of_appointment.isBefore(time_in_15) && start_of_appointment.isAfter(ZonedDateTime.now());
            if (appointment_is_in_15) {
                System.out.println("Appointment added: " + appointment);
                appointments_in_next_15_minutes.add(appointment);
            }
        }
        return appointments_in_next_15_minutes;
    }


    /**
     * Returns total appointments based on selection of a month filter, encompassing all other filters if applicable.
     * @param event
     * @throws SQLException
     */
    @FXML
    void onSelectMonthDropDown(ActionEvent event) throws SQLException {
        String month = MonthDropDown.getValue();
        String year = YearDropDown.getValue();
        String type = TypeDropDown.getValue();

        String count_of_appointments = Integer.toString((new AppointmentDaoMySQL()).getCountOfFilteredAppointments(month, year, type));

        NumberOfAppointments.setText(count_of_appointments);
    }

    /**
     * Returns total appointments based on selection of a type filter, encompassing all other filters if applicable.
     * @param event
     * @throws SQLException
     */
    @FXML
    void onSelectTypeDropdown(ActionEvent event) throws SQLException {
        onSelectMonthDropDown(event);
    }

    /**
     * Returns total appointments based on selection of a year filter, encompassing all other filters if applicable.
     * @param event
     * @throws SQLException
     */
    @FXML
    void onSelectYearDropdown(ActionEvent event) throws SQLException {
        onSelectMonthDropDown(event);
    }

    /**
     * Shows a schedule based on appointment location.
     * @param event
     * @throws SQLException
     */
    @FXML
    void onClickLocationDropDown(ActionEvent event) throws SQLException {
        AppointmentDao appointmentDao = new AppointmentDaoMySQL();
        ObservableList<Appointment> appointments_by_location = appointmentDao.getFutureAppointmentsByLocation(LocationDropDown.getValue());
        LocationTable.setItems(appointments_by_location);
        LocationTitleTxt.setText(LocationDropDown.getValue() + " location's schedule.");
    }

    /**
     * Initializes the main view. First, sets tables for customers and appointments, then call checking to see
     * @throws SQLException
     */
    @FXML
    public void initialize() throws SQLException {
        // set tables for customers and appointments
        CustomerDao customerDao = new CustomerDaoMySQL();
        customerIDCollumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customer_id"));
        customerNameCollumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customer_name"));
        customerAddressCollumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        customerPostalCodeCollumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("postal_code"));
        customerPhoneCollumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        customerCreateDateCollumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("create_date"));
        customerCreatedByCollumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("created_by"));
        customerLastUpdatedCollumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("last_update"));
        customerLastUpdatedByCollumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("last_updated_by"));
        customerDivisionCollumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("division"));
        customersTable.setItems(customerDao.getAllCustomers());

        AppointmentDao appointmentDao = new AppointmentDaoMySQL();
        appointmentsIDCollumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointment_id"));
        appointmentsTitleCollumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        appointmentsDescriptionCollumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        appointmentsLocationCollumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        appointmentsContactIdCollumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contact_id"));
        appointmentsTypeCollumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        appointmentsStartTimeCollumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("start"));
        appointmentsEndTimeCollumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("end"));
        appointmentsCustomerIdCollumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("customer_id"));
        appointmentsUserIdCollumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("user_id"));
        ObservableList<Appointment> allAppointments = appointmentDao.getAllAppointments();
        appointmentsTable.setItems(allAppointments);

        ContactIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointment_id"));
        ContactTitleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        ContactDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        ContactTypeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        ContactStartColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("start"));
        ContactEndColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("end"));
        ContactCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("customer_id"));

        LocationIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointment_id"));
        LocationTitleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        LocationDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        LocationTypeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        LocationStartColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("start"));
        LocationEndColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("end"));
        LocationCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("customer_id"));

        // check to see if there is an appointment in the next 15 minutes
        ObservableList<Appointment> appointmentsInNext15Minutes = getAppointmentsInNext15Minutes(allAppointments);
        String appointments_alert = "You have the following appointment(s) in the next 15 minutes: \n";
        for (Appointment appointment: appointmentsInNext15Minutes) {
            appointments_alert = appointments_alert + "    Appointment_ID: " + Integer.toString(appointment.getAppointment_id()) + ", Type: " + appointment.getType() + ", Time: " + appointment.getStart() +  "   ";
        }
        UpcomingAppointmentsTxt.setText(appointments_alert);

        // set items for dropdown for the count report
        ObservableList<String> allTypes = appointmentDao.getAllTypes();
        allTypes.add("All");
        ObservableList<String> allYears = appointmentDao.getAllYears();
        allYears.add("All");
        ObservableList<String> allMonths = appointmentDao.getAllMonths();
        allMonths.add("All");
        TypeDropDown.setItems(allTypes);
        TypeDropDown.setValue("All");
        MonthDropDown.setItems(allMonths);
        MonthDropDown.setValue("All");
        YearDropDown.setItems(allYears);
        YearDropDown.setValue("All");

        String month = MonthDropDown.getValue();
        String year = YearDropDown.getValue();
        String type = TypeDropDown.getValue();

        // get and set initial count of appointments with no filter
        String count_of_appointments = Integer.toString(appointmentDao.getCountOfFilteredAppointments(month, year, type));
        NumberOfAppointments.setText(count_of_appointments);

        //Set Contact Schedule report dropdown items
        ContactDropDown.setItems((new ContactDaoMySQL().getAllContactNames()));

        //Set Location Schedule report dropdown items
        LocationDropDown.setItems(appointmentDao.getAllLocationNames());
    }
}
