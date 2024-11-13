package edu.metrostate.booknow.Controllers;

import edu.metrostate.booknow.Services.UserService;
import edu.metrostate.booknow.Utils.UIUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class CreateNewAcccountController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    private final UserService userService;

    public CreateNewAcccountController() {
        this.userService = new UserService();
    }

    @FXML
    public void initialize() {
    }

    public void onCreateAccountButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            UIUtil.displayAlert("Error", "All fields must be filled!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            UIUtil.displayAlert("Error", "Password and confirm password do not match");
            return;
        }

        try {
            boolean userExists = userService.login(username, password);
            if (userExists) {
                UIUtil.displayAlert("Error", "Username already exists!");
                return;
            }

            boolean accountCreated = userService.createAccount(username, password);
            if (accountCreated) {
                UIUtil.USER = username;
                UIUtil.displayAlert("Success", "Account created successfully");
                UIUtil.displayScene(getClass().getResource("/edu/metrostate/booknow/BookNowView.fxml"), event);
            } else {
                UIUtil.displayAlert("Error", "Error creating account");
            }
        } catch (SQLException e) {
            UIUtil.displayAlert("Error", e.getMessage());
        }
    }

    public void onLoginButton(ActionEvent event) throws IOException {
        UIUtil.displayScene(getClass().getResource("/edu/metrostate/booknow/LoginView.fxml"), event);
    }
}