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

    public RestaurantDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<String> fetchCityNames() {
        List<String> cityNames = new ArrayList<>();
        String sql = "SELECT DISTINCT city FROM restaurants";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement prepare = conn.prepareStatement(sql)) {

            try (ResultSet resultSet = prepare.executeQuery()) {
                while (resultSet.next()) {
                    cityNames.add(resultSet.getString("city"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cityNames;
    }

    public List<String> fetchCuisineTypes() {
        List<String> cuisineTypes = new ArrayList<>();
        String sql = "SELECT DISTINCT cuisine_type FROM restaurants";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement prepare = conn.prepareStatement(sql)) {

            try (ResultSet resultSet = prepare.executeQuery()) {
                while (resultSet.next()) {
                    cuisineTypes.add(resultSet.getString("cuisine_type"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cuisineTypes;
    }

    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        String query = "SELECT * FROM restaurants";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("restaurant_id");
                String name = resultSet.getString("name");
                String city = resultSet.getString("city");
                String cuisineType = resultSet.getString("cuisine_type");
                String description = resultSet.getString("description");
                String menuPdf = resultSet.getString("menu_pdf");
                String imagePath = resultSet.getString("image_path");
                int maxGuests = resultSet.getInt("max_guests");
                restaurants.add(new Restaurant(id, name, city, cuisineType, description, menuPdf, imagePath, maxGuests));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    public List<Restaurant> getAvailableRestaurants(String selectedCity, String selectedCuisineType, int totalGuests, LocalDate selectedDate) {
        List<Restaurant> availableRestaurants = new ArrayList<>();

        String query = "SELECT DISTINCT r.* " +
                "FROM restaurants r " +
                "JOIN tables t ON r.restaurant_id = t.restaurant_id " +
                "LEFT JOIN reservations res ON t.table_number = res.table_number " +
                "AND res.reservation_date = ? " +
                "LEFT JOIN time_slots ts ON ts.slot_id = res.time_slot_id " +
                "WHERE r.city = ? " +
                "AND r.cuisine_type = ? " +
                "AND t.number_of_seats >= ? " +
                "AND (res.reservation_id IS NULL OR ts.slot_id IS NULL)";

        try (Connection conn = dbConnection.getConnection(); // Use dbConnection.getConnection()
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, java.sql.Date.valueOf(selectedDate));
            stmt.setString(2, selectedCity);
            stmt.setString(3, selectedCuisineType);
            stmt.setInt(4, totalGuests);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantId(rs.getInt("restaurant_id"));
                restaurant.setName(rs.getString("name"));
                restaurant.setCity(rs.getString("city"));
                restaurant.setCuisineType(rs.getString("cuisine_type"));
                restaurant.setDescription(rs.getString("description"));
                restaurant.setMenuPdf(rs.getString("menu_pdf"));
                restaurant.setImagePath(rs.getString("image_path"));
                restaurant.setMaxGuests(rs.getInt("max_guests"));

                availableRestaurants.add(restaurant);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableRestaurants;
    }

}
