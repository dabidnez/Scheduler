package controller;

import dao.*;
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
import model.Country;
import model.Customer;
import model.FirstLevelDivision;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Handles view of creating a customer.
 */
public class CreateViewController {
    private ObservableList<Country> countries;
    private ObservableList<FirstLevelDivision> divisions;

    @FXML
    private ComboBox<String> CountryDropDown;

    @FXML
    private ComboBox<String> DivisionDropDown;

    @FXML
    private Text addressErrorTxt;

    @FXML
    private Text divisionErrorTxt;

    @FXML
    private Text countryErrorTxt;

    @FXML
    private Text nameErrorTxt;

    @FXML
    private Text phoneErrorTxt;

    @FXML
    private Text postErrorTxt;

    @FXML
    private TextField IdTxt;

    @FXML
    private TextField NameTxt;

    @FXML
    private TextField AddressTxt;

    @FXML
    private TextField PostalTxt;

    @FXML
    private TextField PhoneTxt;

    private String username;

    private boolean update;

    /**
     * Handles transfer of username data from main view to this (create customer) view.
     * @param username
     */
    void sendUsername(String username) {
        this.username = username;
    }

    /**
     * Handles transfer of customer data and filling out the create form in the event of an update form.
     * @param customer
     */
    void sendUpdateInfo(Customer customer) {
        update = true;
        IdTxt.setText(Integer.toString(customer.getCustomer_id()));
        NameTxt.setText(customer.getCustomer_name());
        AddressTxt.setText(customer.getAddress());
        CountryDropDown.setValue(customer.getDivision().getCountry().getCountry());
        DivisionDropDown.setValue(customer.getDivision().getDivison());
        PostalTxt.setText(customer.getPostal_code());
        PhoneTxt.setText(customer.getPhone());
    }

    /**
     * Resets error message before another round of input validation.
     */
    private void resetErrors() {
        nameErrorTxt.setText("");
        addressErrorTxt.setText("");
        countryErrorTxt.setText("");
        divisionErrorTxt.setText("");
        postErrorTxt.setText("");
        phoneErrorTxt.setText("");
    }

    /**
     * Transforms list of Country objects to a list of their names in String form.
     * @param countries
     * @return countryNames
     */
    private ObservableList<String> getCountryNames(ObservableList<Country> countries) {
        ObservableList<String> countryNames = FXCollections.observableArrayList();
        for (Country country: countries) {
            countryNames.add(country.getCountry());
        }
        return countryNames;
    }

    /**
     * Transforms list of Division objects into a list of their names in String form.
     * @param divisions
     * @return divisionNames
     */
    private ObservableList<String> getDivisionNames(ObservableList<FirstLevelDivision> divisions) {
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        for (FirstLevelDivision division: divisions) {
            divisionNames.add(division.getDivison());
        }
        return divisionNames;
    }

    /**
     * Transforms list of Division objects to a list of their names in String form.
     * Used lambda function for readability since it is transforming a list.
     * @param country_name
     * @return
     */
    private ObservableList<String> getDivisionNamesByCountryName(String country_name) {
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        divisions.forEach(division -> {
            if (division.getCountry().getCountry().equals(country_name))
                divisionNames.add(division.getDivison());
        });
        return divisionNames;
    }

    /**
     * Return a division object by its name.
     * @param division_name
     * @return
     */
    private FirstLevelDivision getDivisionByName(String division_name) {
        for(FirstLevelDivision division: divisions) {
            if (division.getDivison().equals(division_name)) {
                return division;
            }
        }
        return null;
    }

    /**
     * Handles cancel button, returns window to main view.
     * @param event
     * @throws IOException
     */
    @FXML
    void onClickCancelButton(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/main-view.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Handles changing list of divisions depending on the country selected.
     * @param event
     */
    @FXML
    void onSelectCountry(ActionEvent event) {
        //set division items to type of country selected
        System.out.println("country selected!");
        DivisionDropDown.setItems(getDivisionNamesByCountryName(CountryDropDown.getValue()));
    }

    /**
     * Handles add button. First, it goes through input validation and sets error message depending on whether the input is valid.
     * If all input is valid, either create a new customer or update an existing one if it is in update mode.
     * @param event
     * @throws IOException
     */
    @FXML
    void onClickAddButton(ActionEvent event) throws IOException {
        resetErrors();
        boolean error = false;

        String customer_name = NameTxt.getText();
        if (customer_name.isBlank()) {
            nameErrorTxt.setText("enter a valid name");
            error = true;
        }

        String address = AddressTxt.getText();
        if (address.isBlank()) {
            addressErrorTxt.setText("enter a valid address");
            error = true;
        }

        String country = CountryDropDown.getValue();
        if (country == null) {
            countryErrorTxt.setText("select a country");
            error = true;
        }

        String division = DivisionDropDown.getValue();
        if (division == null) {
            divisionErrorTxt.setText("select a division");
            error = true;
        }

        String postal_code = PostalTxt.getText();
        if (postal_code.isBlank()) {
            postErrorTxt.setText("enter a valid postal code");
            error = true;
        }

        String phone = PhoneTxt.getText();
        if (phone.isBlank()) {
            phoneErrorTxt.setText("enter a valid phone number");
            error = true;
        }

        if (!error) {
            if (update) {
                CustomerDao dao = new CustomerDaoMySQL();
                dao.updateCustomer(Integer.parseInt(IdTxt.getText()),
                        NameTxt.getText(),
                        AddressTxt.getText(),
                        PostalTxt.getText(),
                        PhoneTxt.getText(),
                        username, //update user
                        getDivisionByName(DivisionDropDown.getValue()).getDivision_id());
            } else {
                Customer new_customer = new Customer(1,
                        NameTxt.getText(),
                        AddressTxt.getText(),
                        PostalTxt.getText(),
                        PhoneTxt.getText(),
                        ZonedDateTime.now(),
                        username,
                        ZonedDateTime.now(),
                        username,
                        getDivisionByName(DivisionDropDown.getValue()));
                CustomerDao dao = new CustomerDaoMySQL();
                dao.insertCustomer(new_customer);
            }
            onClickCancelButton(event);
        } else {
            System.out.println("Input Invalid!");
        }
    }

    /**
     * Initializes the dropdowns for country and first level divisions.
     */
    @FXML
    void initialize() {
        System.out.println("arrived at createviewcontroller initialize");
        CountryDao countryDao = new CountryDaoMySQL();
        countries = countryDao.getAllCountries();
        ObservableList<String> countryNames = getCountryNames(countries);
        for (String country: countryNames) {
            System.out.println("country: " + country);
        }
        CountryDropDown.setItems(countryNames);

        DivisionDao divisionDao = new DivisionDaoMySQL();
        divisions = divisionDao.getAllDivisions();
    }
}
