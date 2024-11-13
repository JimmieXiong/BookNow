package edu.metrostate.booknow.DAO;

import edu.metrostate.booknow.Utils.DBConnection;
import edu.metrostate.booknow.Models.Review;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final DBConnection dbConnection;

    public UserDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public boolean createAccount(String username, String password) throws SQLException {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean login(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public boolean checkUserRecordExists(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getUserIdByUserName(String username) {
        String query = "SELECT user_id FROM users WHERE username = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("user_id") : -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Review> getReviewsByUsername(String username) {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT r.review_id, u.username, res.name, r.rating, r.feedback, r.date_of_experience " +
                "FROM reviews r " +
                "JOIN users u ON r.user_id = u.user_id " +
                "JOIN restaurants res ON r.restaurant_id = res.restaurant_id " +
                "WHERE u.username = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reviews.add(new Review(
                        rs.getInt("review_id"),
                        username,
                        rs.getString("name"),
                        rs.getInt("rating"),
                        rs.getString("feedback"),
                        rs.getDate("date_of_experience").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public void submitReview(String username, int restaurantId, int reservationId, int rating, String feedback, LocalDate dateOfExperience) {
        int userId = getUserIdByUserName(username);
        String query = "INSERT INTO reviews (user_id, restaurant_id, reservation_id, rating, feedback, date_of_experience) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, restaurantId);
            stmt.setInt(3, reservationId);
            stmt.setInt(4, rating);
            stmt.setString(5, feedback);
            stmt.setDate(6, Date.valueOf(dateOfExperience));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
