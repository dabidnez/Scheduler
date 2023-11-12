
package controller;

import dao.AppointmentDao;
import dao.AppointmentDaoMySQL;
import dao.CustomerDao;
import dao.CustomerDaoMySQL;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class MainViewController {
    @FXML
    private TableView<Appointment> appointmentsTable;

    @FXML
    private TableColumn<Appointment, String> appoimtmentsLastUpdatedByCollumn;

    @FXML
    private TableColumn<Appointment, String> appointmentsContactCollumn;

    @FXML
    private TableColumn<Appointment, String> appointmentsCustomerCollumn;

    @FXML
    private TableColumn<Appointment, String> appointmentsDescriptionCollumn;

    @FXML
    private TableColumn<Appointment, String> appointmentsEndTimeCollumn;

    @FXML
    private TableColumn<Appointment, String> appointmentsIDCollumn;

    @FXML
    private TableColumn<Appointment, String> appointmentsLastUpdatedCollumn;

    @FXML
    private TableColumn<Appointment, String> appointmentsLocationCollumn;

    @FXML
    private TableColumn<Appointment, String> appointmentsStartTimeCollumn;

    @FXML
    private TableColumn<Appointment, String> appointmentsTitleCollumn;

    @FXML
    private TableColumn<Appointment, String> appointmentsTypeCollumn;

    @FXML
    private TableColumn<Appointment, String> appointmentsUserCollumn;




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
    private Label welcomeTxt;

    @FXML
    private Button DeleteCustomerButton;

    private String username;

    void sendUsername(String username) {
        this.username = username;
    }

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

    @FXML
    void onClickDeleteButton(ActionEvent event) {
        CustomerDao customerDao = new CustomerDaoMySQL();
        if (DeleteCustomerButton.getText().equals("D E L E T E")) {
            DeleteCustomerButton.setText("Confirm? This will delete all associated appoointments.");
            return;
        }
        try {
            Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
            new AppointmentDaoMySQL().deleteAssociatedAppointments(selectedCustomer.getCustomer_id());
            customerDao.deleteCustomer(selectedCustomer.getCustomer_id());
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        DeleteCustomerButton.setText("D E L E T E");
        customersTable.setItems(customerDao.getAllCustomers());
    }

    @FXML
    void onClickUpdateButton(ActionEvent event) {

    }


    @FXML
    public void initialize() {
        CustomerDao customerDao = new CustomerDaoMySQL();
        AppointmentDao appointmentDao = new AppointmentDaoMySQL();
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
    }

}
