package edu.metrostate.booknow.Services;

import edu.metrostate.booknow.DAO.UserDAO;
import edu.metrostate.booknow.Models.User;

import java.sql.SQLException;

/**
 * Service class for managing user-related functionalities such as login, account creation, and input validation.
 */

public class UserServices {

    private final UserDAO userDAO;
    private static User currentUser;

    public UserServices() {
        this.userDAO = new UserDAO();
    }

    // Uses UserDAO.login() method to handle login logic
    public boolean login(String username, String password) {
        try {
            if (userDAO.login(username, password)) {
                currentUser = userDAO.getUserByUsername(username);
                return true; // Login successful
            } else {
                return false; // Login failed
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false; // Return false on failure
        }
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
        // not implemented yet
        currentUser = null;
    }

    // Validates user input for account creation and provides specific error messages for multiple conditions
    public String validateInput(String username, String password, String confirmPassword) {
        StringBuilder errorMessage = new StringBuilder();

        // Check if username and confirm password are not empty but password is empty
        if (!username.isEmpty() && password.isEmpty() && !confirmPassword.isEmpty()) {
            errorMessage.append("Password cannot be empty!\n");
        } else {
            // Check if username is empty
            if (username.isEmpty()) {
                errorMessage.append("Username cannot be empty!\n");
            } else if (username.length() < 8) {
                errorMessage.append("Username must be at least 8 characters long!\n");
            }

            // Check if password is empty or too short
            if (password.isEmpty()) {
                errorMessage.append("Password cannot be empty!\n");
            } else if (password.length() < 8) {
                errorMessage.append("Password must be at least 8 characters long!\n");
            }

            // Check if confirm password is empty or doesn't match
            if (confirmPassword.isEmpty()) {
                errorMessage.append("Confirm password field cannot be empty!\n");
            } else if (!password.equals(confirmPassword)) {
                errorMessage.append("Password and confirm password do not match!\n");
            }
        }
        // If no errors, return null, otherwise return all errors
        return errorMessage.isEmpty() ? null : errorMessage.toString();
    }
}
