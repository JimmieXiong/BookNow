package edu.metrostate.booknow.Controllers;

import edu.metrostate.booknow.DAO.UserDAO;
import edu.metrostate.booknow.Services.AuthenticationService;
import edu.metrostate.booknow.Utils.DBConnection;
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

    // Called during the loading of LoginView.fxml
    public LoginController() {
        // Initialize dependencies for authentication
        DBConnection DBConnection = new DBConnection(); // Establish a database connection
        // (DAO) Data access object for user operations takes (DBConnection) to be able to connect to the database
        UserDAO userDAO = new UserDAO(DBConnection);
        // Handles authentication using UserDAO
        this.authenticationService = new AuthenticationService(userDAO);
    }

    public void onLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            boolean success = authenticationService.login(username, password);
            if (success) {
                UIUtil.USER = username;
                UIUtil.displayAlert("Success", "Login successful");
                UIUtil.displayScene(getClass().getResource("/edu/metrostate/booknow/BookNowView.fxml"), event);
            } else {
                UIUtil.displayAlert("Error", "Invalid username or password");
            }
        } catch (SQLException e) {
            UIUtil.displayAlert("Error", "Database error: " + e.getMessage());
        }
    }

    public void onCreateAccountButtonAction(ActionEvent event) {
        UIUtil.displayScene(getClass().getResource("/edu/metrostate/booknow/CreateAccountView.fxml"), event);
    }
}
