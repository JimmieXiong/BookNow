package edu.metrostate.booknow.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class to manage database connections for the application.
 * Provides a method to establish a connection to a predefined MySQL database.
 */

public class DB_Connection_Util {

    private static final String url = "jdbc:mysql://localhost:3306/booknow";
    private static final String user = "root";
    private static final String password = "root";

    // Utility method to establish database connection
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
        }
        return null;
    }
}
