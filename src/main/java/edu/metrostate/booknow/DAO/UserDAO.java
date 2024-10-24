package edu.metrostate.booknow.DAO;

import edu.metrostate.booknow.Models.User;
import edu.metrostate.booknow.Utils.DB_Connection_Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) class for managing user-related operations in the database.
 */

public class UserDAO {

    // Method to retrieve user by username
    public User getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DB_Connection_Util.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("password")  // Removed 'role'
                    );
                }
            }
        }
        return null; // Return null if user is not found
    }


    // handles db query in logging in
    public boolean login(String username, String password) throws SQLException {

        // SQL query to select user by username and password
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        // Using try-with-resources to ensure the connection and statement are closed
        try (Connection conn = DB_Connection_Util.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(query)) {

                // Set the parameters for the SQL query
                stmt.setString(1, username);
                stmt.setString(2, password);

                // Execute the query and get the result set
                try (ResultSet rs = stmt.executeQuery()) {
                    // Return true if a user is found (login successful), otherwise false
                    return rs.next();
                }
            }
        }
    }

    // Method to create a new account
    public boolean createAccount(String username, String password) throws SQLException {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";  // Removed 'role'

        try (Connection conn = DB_Connection_Util.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if account creation is successful
        }
    }

    // Method to check if a user exists
    public boolean checkUserRecordExists(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DB_Connection_Util.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Return true if user record exists
            }
        }
    }
}
