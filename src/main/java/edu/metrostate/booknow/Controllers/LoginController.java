package edu.metrostate.booknow.Controllers;

import edu.metrostate.booknow.DBConnection;
import edu.metrostate.booknow.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField tf_Username;

    @FXML
    private PasswordField pf_Password;

    private DBConnection dbHandler;

    public LoginController() {
        System.out.println("LoginController Constructor Called");
        dbHandler = new DBConnection();
    }

    // Handles login button action
    public void onLoginButtonAction(ActionEvent event) {
        // Retrieve username and password from text fields
        String username = tf_Username.getText();
        String password = pf_Password.getText();

        // Validates the input fields
        if (username.isEmpty() || password.isEmpty()) {
            Util.displayAlert("Validation Error", "Both fields are required!");
            return;
        }

        try {
            // Check login credentials using DBConnection
            boolean loginSuccessful = dbHandler.login(username, password);
            if (loginSuccessful) {
                // Set the global user and switch scene
                Util.setCurrentUser(username);
                Util.displayScene(getClass().getResource("/edu/metrostate/booknow/BookNowView.fxml"), event);
            } else {
                Util.displayAlert("Login Failed", "Invalid username or password.");
            }
        } catch (SQLException | IOException e) {
            Util.displayAlert("Error", "Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Handles the create account button action and switch to Create Account scene
    public void onCreateAccountButtonAction(ActionEvent event) throws IOException {
        Util.displayScene(getClass().getResource("/edu/metrostate/booknow/CreateAccountView.fxml"), event);
    }
}
