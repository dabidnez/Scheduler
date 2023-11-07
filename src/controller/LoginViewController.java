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
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;

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

    @FXML
    void loginPressed(ActionEvent event) throws IOException {
        String input_username = UsernameTextField.getText();
        String input_password = PasswordTextField.getText();
        UserDaoMySQL mysql = new UserDaoMySQL();
        User user = mysql.getUser(input_username);
        if (user == null) {
            ErrorText.setText(rb.getString("error.incorrect.username"));
        } else if (user.getPassword().equals(input_password)) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/main-view.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            ErrorText.setText(rb.getString("error.incorrect.password"));
        }
    }

    @FXML
    void initialize() {
        // Initializing the connection in the beginning, connection pool in a production environment
        JDBC.makeConnection();
        rb = ResourceBundle.getBundle("rb/login", Locale.getDefault());
        LocationText.setText("         " + rb.getString("logging.in.from") + " "  + rb.getString("country." + Locale.getDefault().getDisplayCountry().toLowerCase()));
        UsernameText.setText("       " + rb.getString("username"));
        PasswordText.setText("       " + rb.getString("password"));
        TitleText.setText("               " + rb.getString("login"));
        LoginButton.setText(rb.getString("login"));
    }
}