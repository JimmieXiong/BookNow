package edu.metrostate.booknow.DAO;

import edu.metrostate.booknow.Models.User;
import edu.metrostate.booknow.Utils.DBConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    // SQL queries as constants for reusability if needed.
    private static final String getUserByUsernameQuery = "SELECT * FROM users WHERE username = ?";
    private static final String loginQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
    private static final String createAccountQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
    private static final String checkIfUserExistQuery = "SELECT * FROM users WHERE username = ?";

    /**
     * Retrieves a user from the database by their username.
     *
     * @param username the username of the user to be retrieved
     * @return the User object if found, or null if no user is found with the given username
     * @throws SQLException if any SQL error occurs during the database query
     */
    // Method to retrieve user by username
    public User getUserByUsername(String username) throws SQLException {
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = createPreparedStatement(conn, getUserByUsernameQuery, username);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"));
            }
        }
        return null; // Return null if user is not found
    }

    /**
     * Handles database query for logging in.
     *
     * @param username the username of the user attempting to log in
     * @param password the password of the user attempting to log in
     * @return true if the login is successful (user found with matching username and password), otherwise false
     * @throws SQLException if a database access error occurs
     */
    // Handles database query for logging in
    public boolean login(String username, String password) throws SQLException {
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = createPreparedStatement(conn, loginQuery, username, password);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next(); // Return true if a user is found (login successful), otherwise false
        }
    }

    /**
     * Creates a new user account in the database.
     *
     * @param username the username for the new account
     * @param password the password for the new account
     * @return true if the account is created successfully, false otherwise
     * @throws SQLException if a database access error occurs
     */
    // Method to create a new account
    public boolean createAccount(String username, String password) throws SQLException {
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = createPreparedStatement(conn, createAccountQuery, username, password)) {
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if account creation is successful
        }
    }

    /**
     * Checks if a user record exists in the database based on the provided username.
     *
     * @param username the username of the user to check for existence
     * @return true if a record for the given username exists, false otherwise
     * @throws SQLException if a database access error occurs
     */
    // Method to check if a user exists
    public boolean checkUserRecordExists(String username) throws SQLException {
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = createPreparedStatement(conn, checkIfUserExistQuery, username);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next(); // Return true if user record exists
        }
    }

    /**
     * Helper method to create a PreparedStatement.
     *
     * @param conn the database connection object
     * @param query the SQL query to be prepared
     * @param parameters the parameters to be set in the PreparedStatement
     * @return a PreparedStatement object with the given query and parameters set
     * @throws SQLException if a database access error occurs or this method is called on a closed connection
     */
    // Helper method to create a PreparedStatement
    private PreparedStatement createPreparedStatement(Connection conn, String query, String... parameters) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(query);
        for (int i = 0; i < parameters.length; i++) {
            stmt.setString(i + 1, parameters[i]);
        }
        return stmt;
    }
}