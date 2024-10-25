package edu.metrostate.booknow.Controllers;

import edu.metrostate.booknow.Services.UserServices;
import edu.metrostate.booknow.Utils.UIUtils;
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
            boolean loginSuccess = userService.login(username, password);
            UIUtils.handleLogin(event, loginSuccess, bookNowViewPath, "Invalid username or password.");
        } else {
            UIUtils.showInfoAlert("Validation Error", "Both fields are required!");
        }
    }

    public void onCreateAccountButtonAction(ActionEvent event) throws IOException {
        UIUtils.switchScene(getClass().getResource(createAccountViewPath), event);
    }
}
