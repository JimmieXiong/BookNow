package edu.metrostate.booknow.Services;

import edu.metrostate.booknow.DAO.UserDAO;
import edu.metrostate.booknow.Utils.DBConnection;
import java.sql.SQLException;

/**
 * The AuthenticationService class provides functionalities for user authentication
 * and account creation. It utilizes the UserDAO class for database interactions.
 */

public class AuthenticationService {
    private final UserDAO userDAO;

    // initializes UserDAO with a DBConnection
    public AuthenticationService() {
        this.userDAO = new UserDAO(new DBConnection());
    }

    // Constructor for dependency injection BookNowFacadeService
    public AuthenticationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean login(String username, String password) throws SQLException {
        return userDAO.login(username, password);
    }

    public String validateAndCreateAccount(String username, String password, String confirmPassword) throws SQLException {
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return "All fields must be filled!";
        }

        if (username.length() <= 8) {
            return "Username has to be 8 characters or longer";
        }

        if (password.length() <= 8) {
            return "Password has to be be 8 characters or longer";
        }

        if (!password.equals(confirmPassword)) {
            return "Password and confirm password do not match";
        }

        boolean userExists = userDAO.login(username, password);
        if (userExists) {
            return "Username already exists!";
        }

        boolean accountCreated = userDAO.createAccount(username, password);
        if (accountCreated) {
            return "Success";
        } else {
            return "Error creating account";
        }
    }
}