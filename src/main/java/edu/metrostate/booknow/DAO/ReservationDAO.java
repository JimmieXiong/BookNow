package edu.metrostate.booknow.DAO;

import edu.metrostate.booknow.Utils.DBConnection;
import edu.metrostate.booknow.Models.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class ReservationDAO {
    private final DBConnection dbConnection;

    // Constructor accepts a DBConnection instance
    public ReservationDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    /**
     * Retrieves all reservations for a specific user by username.
     *
     * @param username The username of the user.
     * @return A list of reservations for the user.
     */
    public ObservableList<Reservation> getUserReservations(String username) {
        int userId = getUserIdByUserName(username);
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList();

        String query = "SELECT r.reservation_id, rest.restaurant_id, rest.name, r.reservation_date, t.slot_label, r.table_number " +
                "FROM reservations r " +
                "JOIN restaurants rest ON r.restaurant_id = rest.restaurant_id " +
                "JOIN time_slots t ON r.time_slot_id = t.slot_id " +
                "WHERE r.user_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservation_id");
                int restaurantId = resultSet.getInt("restaurant_id");
                String restaurantName = resultSet.getString("name");
                LocalDate reservationDate = resultSet.getTimestamp("reservation_date").toLocalDateTime().toLocalDate();
                String timeSlot = resultSet.getString("slot_label");
                String tableNumber = resultSet.getString("table_number");

                reservationList.add(new Reservation(reservationId, restaurantId, restaurantName, reservationDate, timeSlot, tableNumber));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservationList;
    }

    /**
     * Cancels a reservation by its ID.
     *
     * @param reservationId The ID of the reservation to cancel.
     */
    public void cancelReservation(int reservationId) {
        String deleteQuery = "DELETE FROM reservations WHERE reservation_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, reservationId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a reservation by its ID.
     *
     * @param reservationId The ID of the reservation.
     * @return The reservation object, or null if not found.
     */
    public Reservation getReservationById(int reservationId) {
        Reservation reservation = null;

        String query = "SELECT r.reservation_id, rest.restaurant_id, rest.name, r.reservation_date, t.slot_label, r.table_number " +
                "FROM reservations r " +
                "JOIN restaurants rest ON r.restaurant_id = rest.restaurant_id " +
                "JOIN time_slots t ON r.time_slot_id = t.slot_id " +
                "WHERE r.reservation_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reservationId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int restaurantId = resultSet.getInt("restaurant_id");
                String restaurantName = resultSet.getString("name");
                LocalDate reservationDate = resultSet.getTimestamp("reservation_date").toLocalDateTime().toLocalDate();
                String timeSlot = resultSet.getString("slot_label");
                String tableNumber = resultSet.getString("table_number");

                reservation = new Reservation(reservationId, restaurantId, restaurantName, reservationDate, timeSlot, tableNumber);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservation;
    }

    /**
     * Checks if a reservation has an associated review.
     *
     * @param reservationId The ID of the reservation.
     * @return True if a review exists for the reservation, false otherwise.
     */
    public boolean checkReservationHasReview(int reservationId) {
        boolean hasReview = false;
        String query = "SELECT COUNT(*) FROM reviews WHERE reservation_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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

    /**
     * Retrieves the user ID based on the username.
     *
     * @param username The username to search for.
     * @return The user ID, or -1 if not found.
     */
    private int getUserIdByUserName(String username) {
        String sql = "SELECT user_id FROM users WHERE username = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement prepare = conn.prepareStatement(sql)) {
            prepare.setString(1, username);
            ResultSet resultSet = prepare.executeQuery();
            return resultSet.next() ? resultSet.getInt("user_id") : -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
