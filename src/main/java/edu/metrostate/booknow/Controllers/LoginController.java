package edu.metrostate.booknow.Controllers;

import edu.metrostate.booknow.Services.UserService;
import edu.metrostate.booknow.Utils.UIUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField usernameField; // Changed to match FXML
    @FXML
    private PasswordField passwordField; // Changed to match FXML

    private final UserService userService;

    public LoginController() {
        userService = new UserService();
    }

    @FXML
    public void initialize() {
    }


    public void onLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            UIUtil.displayAlert("Error", "Both fields are required!");
            return;
        }

        try {
            boolean loginSuccessful = userService.login(username, password);
            if (loginSuccessful) {
                UIUtil.USER = username;
                UIUtil.displayScene(getClass().getResource("/edu/metrostate/booknow/BookNowView.fxml"), event);
            } else {
                UIUtil.displayAlert("Error", "Invalid username or password.");
            }
        } catch (SQLException e) {
            UIUtil.displayAlert("Error", e.getMessage());
            e.printStackTrace();
        }
    }

    public void onCreateAccountButtonAction(ActionEvent event) throws IOException {
        UIUtil.displayScene(getClass().getResource("/edu/metrostate/booknow/CreateAccountView.fxml"), event);
    }
}