package controller;

import dao.UserDaoMySQL;
import helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import javax.xml.stream.Location;
import java.io.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Handles view of login page.
 */
public class LoginViewController {
    Stage stage;
    Parent scene;
    ResourceBundle rb;

    @FXML
    private Label ErrorText;

    @FXML
    private Label LocationText;

    @FXML
    private TextField PasswordTextField;

    @FXML
    private TextField UsernameTextField;

    @FXML
    private Button LoginButton;

    @FXML
    private Label PasswordText;

    @FXML
    private Label UsernameText;

    @FXML
    private Label TitleText;

    /**
     * Record a log entry to log.txt if there is a failed login attempt with timestamp.
     * @throws IOException
     */
    private void recordFailedLogin() throws IOException {
        FileWriter fileWriter = new FileWriter("log.txt", true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(String.format("-- Login Attempt Failed at DateTime: %s --", ZonedDateTime.now()));
        printWriter.close();
        fileWriter.close();
        System.out.println("recorded failed login");
    }

    /**
     * Record a log entry to log.txt if there is a successful login attempt with timestamp.
     * @throws IOException
     */
    private void recordSuccessfulLogin() throws IOException {
        FileWriter fileWriter = new FileWriter("log.txt", true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(String.format("-- Login Attempt Succeeded at DateTime: %s --", ZonedDateTime.now()));
        printWriter.close();
        fileWriter.close();
        System.out.println("recorded successful login");
    }

    /**
     * Handles login, checks username and password and continues to main view if successful, throws an error message if not.
     * @param event
     * @throws IOException
     */
    @FXML
    void loginPressed(ActionEvent event) throws IOException {
        String input_username = UsernameTextField.getText();
        String input_password = PasswordTextField.getText();
        UserDaoMySQL mysql = new UserDaoMySQL();
        User user = mysql.getUser(input_username);
        if (user == null) {
            ErrorText.setText(rb.getString("error.incorrect.username"));
            recordFailedLogin();
        } else if (user.getPassword().equals(input_password)) {
            recordSuccessfulLogin();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/main-view.fxml"));
            loader.load();

            MainViewController mainViewController = loader.getController();
            mainViewController.sendUsername(input_username);
            scene = loader.getRoot();

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            ErrorText.setText(rb.getString("error.incorrect.password"));
            recordFailedLogin();
        }
    }

    /**
     * Sets initial timezone and language specific text in the beginning. Also starts database connection.
     */
    @FXML
    void initialize() {
        // Initializing the connection in the beginning, connection pool in a production environment
        JDBC.makeConnection();
        rb = ResourceBundle.getBundle("rb/login", Locale.getDefault());
        LocationText.setText("         " + rb.getString("logging.in.from") + " "  + ZoneId.systemDefault());
        UsernameText.setText("       " + rb.getString("username"));
        PasswordText.setText("       " + rb.getString("password"));
        TitleText.setText("               " + rb.getString("login"));
        LoginButton.setText(rb.getString("login"));
    }
}
