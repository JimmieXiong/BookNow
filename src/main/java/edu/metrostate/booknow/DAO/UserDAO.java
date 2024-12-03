package edu.metrostate.booknow.DAO;

import edu.metrostate.booknow.Utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The UserDAO class provides methods to interact with the user-related data
 * in the database. It allows for creating new user accounts and authenticating
 * existing users through querying the database.
 */

public class UserDAO {

    private final DBConnection DBConnection;

    public UserDAO(DBConnection dbConnection) {
        this.DBConnection = dbConnection;
    }

    /**
     * Creates a new user account in the database.
     *
     * @param username the username of the account
     * @param password the password of the account
     * @return true if the account was successfully created, false otherwise
     */
    public boolean createAccount(String username, String password) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        return executeUpdate(query, username, password);
    }

    /**
     * Authenticates a user by checking their username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return true if the username and password match a record in the database, false otherwise
     */
    public boolean login(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = prepareStatement(conn, query, username, password);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next(); // Returns true if a record is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Fetches the user ID associated with a given username from the database.
     *
     * @param username the username of the user whose ID is to be fetched
     * @return the user ID if the username exists in the database, -1 otherwise
     */
    public int getUserIdByUserName(String username) {
        String query = "SELECT user_id FROM users WHERE username = ?";

        // Attempt to execute SQL query to fetch the user ID by username
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = prepareStatement(conn, query, username);
             ResultSet rs = stmt.executeQuery()) {

            // Return user_id if the result set contains data; otherwise, return -1
            return rs.next() ? rs.getInt("user_id") : -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Executes a query that modifies the database (INSERT, UPDATE, DELETE).
     *
     * @param query      the SQL query to execute
     * @param parameters the parameters for the query
     * @return true if the operation affected at least one row, false otherwise
     */
    private boolean executeUpdate(String query, Object... parameters) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = prepareStatement(conn, query, parameters)) {
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Prepares a SQL statement with the given parameters.
     *
     * @param conn       the database connection
     * @param query      the SQL query
     * @param parameters the parameters for the query
     * @return the prepared SQL statement
     * @throws SQLException if a database access error occurs
     */
    private PreparedStatement prepareStatement(Connection conn, String query, Object... parameters) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(query);
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i] instanceof Integer) {
                stmt.setInt(i + 1, (Integer) parameters[i]);
            } else if (parameters[i] instanceof String) {
                stmt.setString(i + 1, (String) parameters[i]);
            } else if (parameters[i] instanceof java.sql.Date) {
                stmt.setDate(i + 1, (java.sql.Date) parameters[i]);
            } else if (parameters[i] == null) {
                stmt.setNull(i + 1, java.sql.Types.NULL);
            }
        }
        return stmt;
    }
}
