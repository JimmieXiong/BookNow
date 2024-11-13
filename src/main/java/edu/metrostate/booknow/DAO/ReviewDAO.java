package edu.metrostate.booknow.DAO;

import edu.metrostate.booknow.Utils.DBConnection;
import edu.metrostate.booknow.Models.Review;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {
    private final DBConnection dbConnection;
    private final UserDAO userDAO;

    public ReviewDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
        this.userDAO = new UserDAO(dbConnection); // Use DBConnection to initialize UserDAO
    }

    public List<Review> getReviewsByUsername(String username) {
        int userId = userDAO.getUserIdByUserName(username);

        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.review_id, u.username, res.name, r.rating, r.feedback, r.date_of_experience " +
                "FROM reviews r " +
                "JOIN users u ON r.user_id = u.user_id " +
                "JOIN restaurants res ON r.restaurant_id = res.restaurant_id " +
                "WHERE r.user_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int reviewId = rs.getInt("review_id");
                String restaurantName = rs.getString("name");
                int rating = rs.getInt("rating");
                String feedback = rs.getString("feedback");
                LocalDate dateOfExperience = rs.getDate("date_of_experience").toLocalDate();
                reviews.add(new Review(reviewId, username, restaurantName, rating, feedback, dateOfExperience));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public String getAverageRating(int restaurantId) {
        String averageRating = "N/A";
        String query = "SELECT AVG(rating) AS avg_rating FROM reviews WHERE restaurant_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, restaurantId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                double avg = resultSet.getDouble("avg_rating");
                if (!resultSet.wasNull()) {
                    averageRating = String.format("%.1f", avg);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return averageRating;
    }

    public List<Review> getReviewsByRestaurantId(int restaurantId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.review_id, u.username, res.name, r.rating, r.feedback, r.date_of_experience " +
                "FROM reviews r " +
                "JOIN users u ON r.user_id = u.user_id " +
                "JOIN restaurants res ON r.restaurant_id = res.restaurant_id " +
                "WHERE r.restaurant_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, restaurantId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int reviewId = rs.getInt("review_id");
                String username = rs.getString("username");
                String restaurantName = rs.getString("name");
                int rating = rs.getInt("rating");
                String feedback = rs.getString("feedback");
                LocalDate dateOfExperience = rs.getDate("date_of_experience").toLocalDate();
                reviews.add(new Review(reviewId, username, restaurantName, rating, feedback, dateOfExperience));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public void submitReview(String username, int restaurantId, int reservationId, int rating, String feedback, LocalDate dateOfExperience) {
        int userId = userDAO.getUserIdByUserName(username);
        String sql = "INSERT INTO reviews (user_id, restaurant_id, reservation_id, rating, feedback, date_of_experience) VALUES (?,?,?,?,?,?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, restaurantId);
            pstmt.setInt(3, reservationId);
            pstmt.setInt(4, rating);
            pstmt.setString(5, feedback);
            pstmt.setDate(6, Date.valueOf(dateOfExperience));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkReservationHasReview(int reservationId) {
        String query = "SELECT COUNT(*) FROM reviews WHERE reservation_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reservationId);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next() && resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}