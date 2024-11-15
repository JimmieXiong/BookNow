package edu.metrostate.booknow.DAO;

import edu.metrostate.booknow.Utils.DBConnection;
import edu.metrostate.booknow.Models.Restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO {
    private final DBConnection dbConnection;
    private final String AVAILABLE_RESTAURANTS_QUERY = "SELECT DISTINCT r.* " +
            "FROM restaurants r " +
            "JOIN tables t ON r.restaurant_id = t.restaurant_id " +
            "LEFT JOIN reservations res ON t.table_number = res.table_number " +
            "AND res.reservation_date = ? " +
            "LEFT JOIN time_slots ts ON ts.slot_id = res.time_slot_id " +
            "WHERE r.city = ? " +
            "AND r.cuisine_type = ? " +
            "AND t.number_of_seats >= ? " +
            "AND (res.reservation_id IS NULL OR ts.slot_id IS NULL)";

    public RestaurantDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<String> fetchDistinctColumnValues(String columnName, String tableName) {
        List<String> values = new ArrayList<>();
        String sql = "SELECT DISTINCT " + columnName + " FROM " + tableName;
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement prepare = conn.prepareStatement(sql);
             ResultSet resultSet = prepare.executeQuery()) {
            while (resultSet.next()) {
                values.add(resultSet.getString(columnName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return values;
    }

    public List<Restaurant> getAvailableRestaurants(String selectedCity, String selectedCuisineType, int totalGuests, LocalDate selectedDate) {
        List<Restaurant> availableRestaurants = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(AVAILABLE_RESTAURANTS_QUERY)) {
            stmt.setDate(1, java.sql.Date.valueOf(selectedDate));
            stmt.setString(2, selectedCity);
            stmt.setString(3, selectedCuisineType);
            stmt.setInt(4, totalGuests);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    availableRestaurants.add(mapResultSetToRestaurant(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableRestaurants;
    }

    public List<String> fetchCityNames() {
        return fetchDistinctColumnValues("city", "restaurants");
    }

    public List<String> fetchCuisineTypes() {
        return fetchDistinctColumnValues("cuisine_type", "restaurants");
    }

    private Restaurant mapResultSetToRestaurant(ResultSet rs) throws SQLException {
        return new Restaurant(
                rs.getInt("restaurant_id"),
                rs.getString("name"),
                rs.getString("city"),
                rs.getString("cuisine_type"),
                rs.getString("description"),
                rs.getString("menu_pdf"),
                rs.getString("image_path"),
                rs.getInt("max_guests")
        );
    }
}