package edu.metrostate.booknow.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The DBConnection class is responsible for managing the database connection.
 * It provides a method to establish a connection to the database.
 */

public class DBConnection {
    private static final String url = "jdbc:mysql://localhost:3306/booknow";
    private static final String user = "root";
    private static final String password = "password";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            UIUtil.displayAlert("Database Connection Error", "Failed to connect to the database.");
            throw e;
        }
    }
}