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

public class CreateNewAccountController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    private final AuthenticationService authenticationService;

    public CreateNewAccountController() {
        DBConnection dbConnection = new DBConnection();
        UserDAO userDAO = new UserDAO(dbConnection);
        this.authenticationService = new AuthenticationService(userDAO);
    }

    public void onCreateAccountButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        try {
            String resultMessage = authenticationService.validateAndCreateAccount(username, password, confirmPassword);
            if (resultMessage.equals("Success")) {
                UIUtil.USER = username;
                UIUtil.displayAlert("Success", "Account created successfully");
                UIUtil.displayScene(getClass().getResource("/edu/metrostate/booknow/BookNowView.fxml"), event);
            } else {
                UIUtil.displayAlert("Error", resultMessage);
            }
        } catch (SQLException e) {
            UIUtil.displayAlert("Error", e.getMessage());
        }
    }

    public void onLoginButtonAction(ActionEvent event) {
        UIUtil.displayScene(getClass().getResource("/edu/metrostate/booknow/LoginView.fxml"), event);
    }
}
