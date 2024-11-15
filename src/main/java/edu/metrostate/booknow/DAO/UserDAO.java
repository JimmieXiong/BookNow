package edu.metrostate.booknow.DAO;

import edu.metrostate.booknow.Utils.DBConnection;
import java.sql.*;

public class UserDAO {
    private static final String INSERT_USER_QUERY = "INSERT INTO users (username, password) VALUES (?, ?)";
    private static final String SELECT_USER_QUERY = "SELECT * FROM users WHERE username = ? AND password = ?";
    private static final String GET_USER_ID_QUERY = "SELECT user_id FROM users WHERE username = ?";

    private final DBConnection dbConnection;

    public UserDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public boolean createAccount(String username, String password) {
        return executeUpdate(INSERT_USER_QUERY, username, password);
    }

    public boolean login(String username, String password) {
        return checkRecordExists(SELECT_USER_QUERY, username, password);
    }

    public int getUserIdByUserName(String username) {
        String query = GET_USER_ID_QUERY;
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = prepareStatement(conn, query, username);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt("user_id") : -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private boolean checkRecordExists(String query, Object... parameters) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = prepareStatement(conn, query, parameters);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean executeUpdate(String query, Object... parameters) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = prepareStatement(conn, query, parameters)) {
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private PreparedStatement prepareStatement(Connection conn, String query, Object... parameters) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(query);
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i] instanceof Integer) {
                stmt.setInt(i + 1, (Integer) parameters[i]);
            } else if (parameters[i] instanceof String) {
                stmt.setString(i + 1, (String) parameters[i]);
            } else if (parameters[i] instanceof Date) {
                stmt.setDate(i + 1, (Date) parameters[i]);
            }
        }
        return stmt;
    }
}