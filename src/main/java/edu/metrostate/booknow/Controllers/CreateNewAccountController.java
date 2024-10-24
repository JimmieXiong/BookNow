package edu.metrostate.booknow.Controllers;

import edu.metrostate.booknow.Services.UserServices;
import edu.metrostate.booknow.Utils.AlertUtil;
import edu.metrostate.booknow.Utils.SwitchSceneUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller class for handling the creation of new user accounts.
 */

public class CreateNewAccountController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    private final UserServices userService;
    private static final String loginViewPath = "/edu/metrostate/booknow/LoginView.fxml";

    public CreateNewAccountController() {
        this.userService = new UserServices();
    }

    public void onCreateAccountButton(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate inputs and get the error message if any
        String validationMessage = userService.validateInput(username, password, confirmPassword);

        if (validationMessage != null) {
            // Show the specific validation error message(s)
            AlertUtil.showInfoAlert("Validation Error", validationMessage);
            return;
        }

        try {
            if (userService.createAccount(username, password)) {
                AlertUtil.showInfoAlert("Success", "Account created successfully!");
                SwitchSceneUtil.switchScene(getClass().getResource(loginViewPath), event);
            } else {
                AlertUtil.showErrorAlert("Username Error", "Username already exists!");
            }
        } catch (SQLException | IOException e) {
            AlertUtil.showErrorAlert("Account Error", "Failed to create account. Try again.");
        }
    }


    public void onLoginButton(ActionEvent event) throws IOException {
        SwitchSceneUtil.switchScene(getClass().getResource(loginViewPath), event);
    }
}
