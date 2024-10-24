package edu.metrostate.booknow.DAO;

import edu.metrostate.booknow.Models.Restaurant;
import edu.metrostate.booknow.Utils.DB_Connection_Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for accessing restaurant-related data from the database.
 */

public class RestaurantDAO {
    // fetch distinct cities and cuisine from the restaurants table
    private static final String FETCH_DISTINCT_CITY = "SELECT DISTINCT city FROM restaurants";
    private static final String FETCH_DISTINCT_CUISINE = "SELECT DISTINCT cuisine_type FROM restaurants";

    public List<String> fetchCityNames() {
        return fetchDistinctValues(FETCH_DISTINCT_CITY, "city");
    }

    public List<String> fetchCuisineTypes() {
        return fetchDistinctValues(FETCH_DISTINCT_CUISINE, "cuisine_type");
    }

    // fetch distinct values from a specific column in the database based on the provided query
    private List<String> fetchDistinctValues(String sql, String columnName) {
        // Hold distinct values
        List<String> distinctValues = new ArrayList<>();

        try (Connection conn = DB_Connection_Util.getConnection()) {
            // checks to ensure connection to database was successful
            assert conn != null;
            // Prepare the SQL statement and execute the query
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    distinctValues.add(rs.getString(columnName));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return distinctValues;
    }

    /**
     * Retrieves a list of restaurants that are available for reservation on a specified date,
     * filtered by city and cuisine type.
     *
     * @param city        the city where the restaurants are located
     * @param cuisineType the type of cuisine the restaurants offer
     * @param date        the date for which the restaurant availability is being checked
     * @return a list of restaurants that are available for reservation on the specified date
     */
    // Get restaurants available on the given date (checking availability for reservations)
    public List<Restaurant> getAvailableRestaurants(String city, String cuisineType, LocalDate date) {
        List<Restaurant> availableRestaurants = new ArrayList<>();
        String query = "SELECT r.* FROM restaurants r " +
                "LEFT JOIN reservations res ON r.restaurant_id = res.restaurant_id " +
                "AND res.reservation_date = ? " + // Bind the date in the LEFT JOIN
                "WHERE r.city = ? AND r.cuisine_type = ?";

        try (Connection conn = DB_Connection_Util.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, java.sql.Date.valueOf(date)); // Convert LocalDate to SQL Date
            stmt.setString(2, city);
            stmt.setString(3, cuisineType);

            try (ResultSet rs = stmt.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableRestaurants;
    }


    public String getAverageRating(int restaurantId) {
        String averageRating = "N/A";
        // Add logic for calculating the average rating
        return averageRating;
    }
}
