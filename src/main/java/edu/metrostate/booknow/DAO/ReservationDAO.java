package edu.metrostate.booknow.DAO;

import edu.metrostate.booknow.Utils.DBConnection;
import edu.metrostate.booknow.Models.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class ReservationDAO {
    private final DBConnection dbConnection;

    // Constants for SQL queries
    private final String GET_RESERVATIONS_QUERY =
            "SELECT r.reservation_id, rest.restaurant_id, rest.name, r.reservation_date, t.slot_label, r.table_number " +
                    "FROM reservations r " +
                    "JOIN restaurants rest ON r.restaurant_id = rest.restaurant_id " +
                    "JOIN time_slots t ON r.time_slot_id = t.slot_id " +
                    "WHERE r.user_id = ?";

    private final String GET_RESERVATION_BY_ID_QUERY =
            "SELECT r.reservation_id, rest.restaurant_id, rest.name, r.reservation_date, t.slot_label, r.table_number " +
                    "FROM reservations r " +
                    "JOIN restaurants rest ON r.restaurant_id = rest.restaurant_id " +
                    "JOIN time_slots t ON r.time_slot_id = t.slot_id " +
                    "WHERE r.reservation_id = ?";

    private final String DELETE_RESERVATION_QUERY = "DELETE FROM reservations WHERE reservation_id = ?";
    private final String GET_USER_ID_QUERY = "SELECT user_id FROM users WHERE username = ?";
    private final String CHECK_REVIEW_QUERY = "SELECT COUNT(*) FROM reviews WHERE reservation_id = ?";

    public ReservationDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    private Reservation mapRowToReservation(ResultSet resultSet) throws SQLException {
        int reservationId = resultSet.getInt("reservation_id");
        int restaurantId = resultSet.getInt("restaurant_id");
        String restaurantName = resultSet.getString("name");
        LocalDate reservationDate = resultSet.getTimestamp("reservation_date").toLocalDateTime().toLocalDate();
        String timeSlot = resultSet.getString("slot_label");
        String tableNumber = resultSet.getString("table_number");
        return new Reservation(reservationId, restaurantId, restaurantName, reservationDate, timeSlot, tableNumber);
    }

    private ObservableList<Reservation> executeReservationQuery(String query, int parameter) {
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, parameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reservationList.add(mapRowToReservation(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error executing reservation query: " + e.getMessage());
            e.printStackTrace();
        }
        return reservationList;
    }

    public ObservableList<Reservation> getUserReservations(String username) {
        int userId = getUserId(username);
        return executeReservationQuery(GET_RESERVATIONS_QUERY, userId);
    }

    public void cancelReservation(int reservationId) {
        System.out.println("ReservationDAO: Attempting to cancel reservation with ID: " + reservationId);
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVATION_QUERY)) {
            preparedStatement.setInt(1, reservationId);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("ReservationDAO: Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error canceling reservation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Reservation getReservationById(int reservationId) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_RESERVATION_BY_ID_QUERY)) {
            preparedStatement.setInt(1, reservationId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapRowToReservation(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching reservation by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean hasReview(int reservationId) {
        boolean hasReview = false;
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_REVIEW_QUERY)) {
            preparedStatement.setInt(1, reservationId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                hasReview = resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasReview;
    }

    private int getUserId(String username) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement prepare = conn.prepareStatement(GET_USER_ID_QUERY)) {
            prepare.setString(1, username);
            ResultSet resultSet = prepare.executeQuery();
            return resultSet.next() ? resultSet.getInt("user_id") : -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}