package edu.metrostate.booknow.Controllers;

import edu.metrostate.booknow.Services.UserServices;
import edu.metrostate.booknow.Utils.UIUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class CreateNewAccountController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    private final UserServices userServices = new UserServices();
    private static final String pathToLoginViewFXML = "/edu/metrostate/booknow/LoginView.fxml";

    /**
     * Handles the action event triggered when the "Create Account" button is pressed.
     * The method performs input validation, shows validation error messages if any exist,
     * and proceeds with the account creation process when the input is valid.
     *
     * @param event the ActionEvent that triggered the account creation process
     */
    @FXML
    public void onCreateAccountButton (ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        String validationMessage = userServices.validateInput(username, password, confirmPassword);
        if (validationMessage != null) {
            UIUtils.showErrorAlert("Validation Error", validationMessage);
            return;
        }
        performAccountCreation(username, password, event);
    }

    /**
     * Handles the account creation process, including validation and feedback to the user.
     *
     * @param username the desired username for the new account
     * @param password the desired password for the new account
     * @param event the ActionEvent that triggered the account creation process
     */
    private void performAccountCreation(String username, String password, ActionEvent event) {
        try {
            if (userServices.createAccount(username, password)) {
                UIUtils.showSuccessAlert("Success", "Account created successfully!");
                UIUtils.switchScene(getClass().getResource(pathToLoginViewFXML), event);
            } else {
                UIUtils.showErrorAlert("Username Error", "Username already exists!");
            }
        } catch (SQLException e) {
            UIUtils.showSQLException(e, "Failed to create account. Try again.");
        }
    }

    /**
     * Handles the action event triggered when the "Login" button is pressed.
     * This method switches the current scene to the login view.
     *
     * @param event the ActionEvent that triggered the login process
     */
    @FXML
    public void onLoginButton(ActionEvent event) {
        UIUtils.switchScene(getClass().getResource(pathToLoginViewFXML), event);
    }
}