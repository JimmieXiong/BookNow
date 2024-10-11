package edu.metrostate.booknow.Controllers;

import edu.metrostate.booknow.DBConnection;
import edu.metrostate.booknow.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.SQLException;

public class CreateNewAccountController {

    @FXML
    private TextField tf_Username;

    @FXML
    private PasswordField pf_Password;

    @FXML
    private PasswordField pf_ConfirmPassword;

    private DBConnection dbHandler;

    public CreateNewAccountController() {
        System.out.println("CreateNewAccountController Constructor Called");
        dbHandler = new DBConnection();
    }

    // Handles the Create Account button action
    public void onCreateAccountButtonAction(ActionEvent event) {

        String username = tf_Username.getText();
        String password = pf_Password.getText();
        String confirmPassword = pf_ConfirmPassword.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Util.displayAlert("Validation Error", "All fields must be filled!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            Util.displayAlert("Password Mismatch", "Password and confirm password do not match");
            return;
        }

        try {
            // Check if the username already exists
            boolean userExists = dbHandler.checkUserRecordExists(username);
            if (userExists) {
                Util.displayAlert("Username Error", "Username already exists!");
                return;
            }

            // Create the account
            boolean accountCreated = dbHandler.createAccount(username, password);
            if (accountCreated) {
                Util.displayAlert("Success", "Account created successfully!");
                // Redirect to login page after successful account creation
                Util.displayScene(getClass().getResource("/edu/metrostate/booknow/LoginView.fxml"), event);
            } else {
                Util.displayAlert("Account Error", "Failed to create account. Try again.");
            }

        } catch (SQLException | IOException e) {
            Util.displayAlert("Error", "Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Handles the Log in button action and switch to login scene
    public void onButton_Login(ActionEvent event) throws IOException {
        Util.displayScene(getClass().getResource("/edu/metrostate/booknow/LoginView.fxml"), event);
    }
}
