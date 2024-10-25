package edu.metrostate.booknow.Services;

import edu.metrostate.booknow.DAO.UserDao;
import edu.metrostate.booknow.Models.User;

import java.sql.SQLException;

/**
 * Service class for managing user-related functionalities such as login, account creation, and input validation.
 */

public class UserServices {

    private final UserDao userDAO;
    private static User currentUser;

    private static final String MSG_EMPTY_USERNAME = "Username cannot be empty!\n";
    private static final String MSG_SHORT_USERNAME = "Username must be at least 8 characters long!\n";
    private static final String MSG_EMPTY_PASSWORD = "Password cannot be empty!\n";
    private static final String MSG_SHORT_PASSWORD = "Password must be at least 8 characters long!\n";
    private static final String MSG_EMPTY_CONFIRM_PASSWORD = "Confirm password field cannot be empty!\n";
    private static final String MSG_PASSWORD_MISMATCH = "Password and confirm password do not match!\n";

    public UserServices() {
        this.userDAO = new UserDao();
    }

    // Uses UserDAO.login() method to handle login logic
    public boolean login(String username, String password) {
        try {
            if (userDAO.login(username, password)) {
                setCurrentUser(username);
                return true; // Login successful
            } else {
                return false; // Login failed
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false; // Return false on failure
        }
    }

    private void setCurrentUser(String username) throws SQLException {
        currentUser = userDAO.getUserByUsername(username);
    }

    // Method to validate login fields
    public boolean areLoginFieldsValid(String username, String password) {
        return !(username == null || username.isEmpty() || password == null || password.isEmpty());
    }

    // Handles account creation and returns true if account created successfully
    public boolean createAccount(String username, String password) throws SQLException {
        // Check if the username already exists
        if (userDAO.checkUserRecordExists(username)) {
            System.out.println("Username already exists.");
            return false; // Username already taken
        }
        return userDAO.createAccount(username, password);
    }

    // Retrieves the current logged-in user
    public static User getCurrentUser() {
        return currentUser;
    }

    // Logs out the current user
    public void logout() {
        // will implement soon.
        currentUser = null;
    }

    // Validates user input for account creation and provides specific error messages for multiple conditions
    public String validateInput(String username, String password, String confirmPassword) {
        StringBuilder errorMessage = new StringBuilder();
        validateUsername(username, errorMessage);
        validatePassword(password, errorMessage);
        validateConfirmPassword(password, confirmPassword, errorMessage);
        return errorMessage.isEmpty() ? null : errorMessage.toString();
    }

    private void validateUsername(String username, StringBuilder errorMessage) {
        if (username.isEmpty()) {
            errorMessage.append(MSG_EMPTY_USERNAME);
        } else if (username.length() < 8) {
            errorMessage.append(MSG_SHORT_USERNAME);
        }
    }

    private void validatePassword(String password, StringBuilder errorMessage) {
        if (password.isEmpty()) {
            errorMessage.append(MSG_EMPTY_PASSWORD);
        } else if (password.length() < 8) {
            errorMessage.append(MSG_SHORT_PASSWORD);
        }
    }

    private void validateConfirmPassword(String password, String confirmPassword, StringBuilder errorMessage) {
        if (confirmPassword.isEmpty()) {
            errorMessage.append(MSG_EMPTY_CONFIRM_PASSWORD);
        } else if (!password.equals(confirmPassword)) {
            errorMessage.append(MSG_PASSWORD_MISMATCH);
        }
    }
}