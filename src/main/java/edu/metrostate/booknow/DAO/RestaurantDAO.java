package edu.metrostate.booknow.DAO;

import edu.metrostate.booknow.Models.Restaurant;
import edu.metrostate.booknow.Utils.DBConnectionUtil;
import edu.metrostate.booknow.Utils.UIUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO {

    // SQL queries as constants for reusability if needed.
    private static final String fetchDistinctCityQuery = "SELECT DISTINCT city FROM restaurants";
    private static final String fetchDistinctCuisineQuery = "SELECT DISTINCT cuisine_type FROM restaurants";
    private static final String getAvailableRestaurantQuery = "SELECT r.* FROM restaurants r " +
            "LEFT JOIN reservations res ON r.restaurant_id = res.restaurant_id " +
            "AND res.reservation_date = ? " +
            "WHERE r.city = ? AND r.cuisine_type = ?";

    /**
     * Fetches a list of distinct city names from the database.
     *
     * @return a list of city names.
     */
    public List<String> fetchCityNames() {
        return fetchDistinctColumnValues(fetchDistinctCityQuery, "city");
    }

    /**
     * Fetches a list of distinct cuisine types from the database.
     *
     * @return a list of distinct cuisine types available in the database.
     */
    public List<String> fetchCuisineTypes() {
        return fetchDistinctColumnValues(fetchDistinctCuisineQuery, "cuisine_type");
    }

    /**
     * Fetches distinct values from a specified column in the database.
     *
     * @param sql The SQL query to retrieve data from the database.
     * @param columnName The name of the column from which distinct values are to be fetched.
     * @return A list of distinct values from the specified column. Returns an empty list if no values are found or an error occurs.
     */
    private List<String> fetchDistinctColumnValues(String sql, String columnName) {
        List<String> distinctValues = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                distinctValues.add(rs.getString(columnName));
            }
        } catch (SQLException e) {
            UIUtils.showSQLException(e, "Error fetching distinct values");
        }
        return distinctValues;
    }

    /**
     * Retrieves a list of available restaurants based on the provided city,
     * cuisine type, and date. This method will query the database and return
     * the results as a list of {@link Restaurant} objects.
     *
     * @param city the city to filter the available restaurants
     * @param cuisineType the type of cuisine to filter the available restaurants
     * @param date the date to check for restaurant availability
     * @return a list of {@link Restaurant} objects that match the provided criteria
     */
    public List<Restaurant> getAvailableRestaurants(String city, String cuisineType, LocalDate date) {
        List<Restaurant> availableRestaurants = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = createAvailableRestaurantsStatement(conn, city, cuisineType, date);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                availableRestaurants.add(mapToRestaurant(rs));
            }
        } catch (SQLException e) {
            UIUtils.showSQLException(e, "Error retrieving available restaurants");
        }
        return availableRestaurants;
    }

    /**
     * Creates a prepared statement to retrieve available restaurants based on the specified city, cuisine type, and date.
     *
     * @param conn        The database connection object.
     * @param city        The city in which to search for available restaurants.
     * @param cuisineType The type of cuisine to filter the restaurants by.
     * @param date        The date for which to check the restaurant availability.
     * @return A PreparedStatement object configured with the specified parameters.
     * @throws SQLException if a database access error occurs.
     */
    private PreparedStatement createAvailableRestaurantsStatement(Connection conn, String city, String cuisineType, LocalDate date) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(getAvailableRestaurantQuery);
        stmt.setDate(1, java.sql.Date.valueOf(date));
        stmt.setString(2, city);
        stmt.setString(3, cuisineType);
        return stmt;
    }

    /**
     * Maps the current row of the given ResultSet to a Restaurant object.
     *
     * @param rs the ResultSet containing the restaurant data
     * @return a Restaurant object with properties populated from the ResultSet
     * @throws SQLException if an SQL error occurs while accessing the ResultSet
     */
    private Restaurant mapToRestaurant(ResultSet rs) throws SQLException {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(rs.getInt("restaurant_id"));
        restaurant.setName(rs.getString("name"));
        restaurant.setCity(rs.getString("city"));
        restaurant.setCuisineType(rs.getString("cuisine_type"));
        restaurant.setDescription(rs.getString("description"));
        restaurant.setMenuPdf(rs.getString("menu_pdf"));
        restaurant.setImagePath(rs.getString("image_path"));
        return restaurant;
    }

    public String getAverageRating(int restaurantId) {
        // Logic to fetch and calculate the average rating from the database
        return "0"; // Placeholder
    }
}