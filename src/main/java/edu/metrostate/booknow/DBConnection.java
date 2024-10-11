package edu.metrostate.booknow;

import edu.metrostate.booknow.Models.Restaurant;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {

    private final String url = "jdbc:mysql://localhost:3306/booknow";
    private final String user = "root";
    private final String password = "root";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // Method to login a user
    public boolean login(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    // Method to create a new account
    public boolean createAccount(String username, String password) throws SQLException {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Method to check if a user exists
    public boolean checkUserRecordExists(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement prepare = conn.prepareStatement(sql)) {
            prepare.setString(1, username);

            try (ResultSet resultSet = prepare.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Fetch distinct city names from the restaurants table
    public List<String> fetchCityNames() {
        List<String> cityNames = new ArrayList<>();
        String sql = "SELECT DISTINCT city FROM restaurants";

        try (Connection conn = getConnection();
             PreparedStatement prepare = conn.prepareStatement(sql);
             ResultSet resultSet = prepare.executeQuery()) {
            while (resultSet.next()) {
                cityNames.add(resultSet.getString("city"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cityNames;
    }

    // Fetch distinct cuisine types from the restaurants table
    public List<String> fetchCuisineTypes() {
        List<String> cuisineTypes = new ArrayList<>();
        String sql = "SELECT DISTINCT cuisine_type FROM restaurants";

        try (Connection conn = getConnection();
             PreparedStatement prepare = conn.prepareStatement(sql);
             ResultSet resultSet = prepare.executeQuery()) {
            while (resultSet.next()) {
                cuisineTypes.add(resultSet.getString("cuisine_type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cuisineTypes;
    }

    // Fetch available restaurants based on city and cuisine type
    public List<Restaurant> getAvailableRestaurants(String selectedCity, String selectedCuisineType, LocalDate selectedDate) {
        List<Restaurant> availableRestaurants = new ArrayList<>();

        // Simplified query to only check restaurants based on the city and cuisine type
        String query = "SELECT * FROM restaurants WHERE city = ? AND cuisine_type = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, selectedCity);
            stmt.setString(2, selectedCuisineType);

            ResultSet rs = stmt.executeQuery();

            // Add restaurant details to the list
            while (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantId(rs.getInt("restaurant_id"));
                restaurant.setName(rs.getString("name"));
                restaurant.setCity(rs.getString("city"));
                restaurant.setCuisineType(rs.getString("cuisine_type"));
                restaurant.setDescription(rs.getString("description"));
                restaurant.setMenuPdf(rs.getString("menu_pdf"));
                restaurant.setImagePath(rs.getString("image_path"));

                availableRestaurants.add(restaurant);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableRestaurants;
    }
}
