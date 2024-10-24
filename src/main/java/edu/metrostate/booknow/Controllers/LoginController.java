package edu.metrostate.booknow.Controllers;

import edu.metrostate.booknow.Services.UserServices;
import edu.metrostate.booknow.Utils.AlertUtil;
import edu.metrostate.booknow.Utils.SwitchSceneUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Controller class for handling login functionality in a JavaFX application.
 */

public class LoginController {

    private static final String bookNowViewPath = "/edu/metrostate/booknow/BookNowView.fxml";
    private static final String createAccountViewPath = "/edu/metrostate/booknow/CreateAccountView.fxml";

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private final UserServices userService;

    public LoginController() {
        userService = new UserServices();
    }

    public void onLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Move field validation to UserServices
        if (userService.areLoginFieldsValid(username, password)) {
            if (userService.login(username, password)) {
                switchToBookNowScene(event);
            } else {
                AlertUtil.showErrorAlert("Login Failed", "Invalid username or password.");
            }
        } else {
            AlertUtil.showInfoAlert("Validation Error", "Both fields are required!");
        }
    }

    public void onCreateAccountButtonAction(ActionEvent event) throws IOException {
        SwitchSceneUtil.switchScene(getClass().getResource(createAccountViewPath), event);
    }

    private void switchToBookNowScene(ActionEvent event) {
        try {
            SwitchSceneUtil.switchScene(getClass().getResource(bookNowViewPath), event);
        } catch (IOException e) {
            AlertUtil.showErrorAlert("Error", e.getMessage());
        }
    }
}
