package edu.metrostate.booknow.Services;

import edu.metrostate.booknow.DAO.UserDAO;
import java.sql.SQLException;

/**
 * The AuthenticationService class provides functionalities for user authentication
 * and account creation. It utilizes the UserDAO class for database interactions.
 */

public class AuthenticationService {
    private final UserDAO userDAO;

    // Dependency Injection will allow AuthenticationService to use UserDAo instance to perform database-related operations
    public AuthenticationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Attempts to authenticate a user with the given username and password.
     *
     * @param username the username of the user attempting to log in
     * @param password the password of the user attempting to log in
     * @return true if the login credentials are valid and the user is successfully authenticated,
     *         false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean login(String username, String password) throws SQLException {
        return userDAO.login(username, password);
    }

    private String validateAccountDetails(String username, String password, String confirmPassword) {
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return "All fields must be filled!";
        }
        if (username.length() <= 8) {
            return "Username must be at least 8 characters.";
        }
        if (password.length() <= 8) {
            return "Password must be at least 8 characters.";
        }
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match.";
        }
        return "Valid";
    }

    public String validateAndCreateAccount(String username, String password, String confirmPassword) throws SQLException {
        String validationMessage = validateAccountDetails(username, password, confirmPassword);
        if (!validationMessage.equals("Valid")) {
            return validationMessage;
        }
        if (userDAO.login(username, password)) {
            return "Username already exists!";
        }

        boolean accountCreated = userDAO.createAccount(username, password);
        return accountCreated ? "Success" : "Error creating account.";
    }
}
