package edu.metrostate.booknow.Controllers;

import edu.metrostate.booknow.Services.AuthenticationService;
import edu.metrostate.booknow.Utils.UIUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private final AuthenticationService authenticationService;

    public LoginController() {
        this.authenticationService = new AuthenticationService();
    }

    public void onLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            UIUtil.displayAlert("Error", "Both fields are required!");
            return;
        }

        try {
            boolean loginSuccessful = authenticationService.login(username, password);
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

    public void onCreateAccountButtonAction(ActionEvent event) {
        UIUtil.displayScene(getClass().getResource("/edu/metrostate/booknow/CreateAccountView.fxml"), event);
    }
}