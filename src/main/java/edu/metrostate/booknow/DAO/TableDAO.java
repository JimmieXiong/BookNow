package edu.metrostate.booknow.DAO;

import edu.metrostate.booknow.Utils.DBConnection;
import edu.metrostate.booknow.Models.Restaurant;
import edu.metrostate.booknow.Models.Table;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TableDAO {

    private final String SELECT_AVAILABLE_TABLES_QUERY =
            "SELECT t.table_id, t.table_number, t.number_of_seats, t.booking_fee " +
                    "FROM tables t " +
                    "WHERE t.restaurant_id = ? " +
                    "AND t.number_of_seats >= ? " +
                    "AND t.table_id NOT IN (" +
                    "SELECT r.table_number FROM reservations r " +
                    "WHERE r.restaurant_id = ? " +
                    "AND r.reservation_date = ? " +
                    "AND r.time_slot_id IN (" +
                    "SELECT ts.slot_id FROM time_slots ts" +
                    "))";

    private final String INSERT_RESERVATION_QUERY =
            "INSERT INTO reservations (user_id, restaurant_id, booking_time, reservation_date, time_slot_id, table_number)" +
                    " VALUES (?, ?, NOW(), ?, ?, ?)";

    private final String SELECT_TIME_SLOT_ID_QUERY =
            "SELECT slot_id FROM time_slots WHERE slot_label = ?";

    private final String SELECT_USER_ID_QUERY =
            "SELECT user_id FROM users WHERE username = ?";

    private final String SELECT_AVAILABLE_TIME_SLOTS_QUERY =
            "SELECT ts.slot_label FROM time_slots ts " +
                    "LEFT JOIN reservations r ON ts.slot_id = r.time_slot_id " +
                    "AND r.restaurant_id = ? AND r.reservation_date = ? " +
                    "WHERE r.reservation_id IS NULL AND ? <= ?";

    private final DBConnection dbConnection;

    public TableDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<Table> getAvailableTables(int restaurantId, LocalDate date, int numOfGuests) {
        List<Table> availableTables = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_AVAILABLE_TABLES_QUERY)) {
            statement.setInt(1, restaurantId);
            statement.setInt(2, numOfGuests);
            statement.setInt(3, restaurantId);
            statement.setDate(4, java.sql.Date.valueOf(date));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Table table = mapResultSetToTable(resultSet, restaurantId);
                availableTables.add(table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableTables;
    }

    public boolean reserveTable(String username, int restaurantId, LocalDate reservationDate, String selectedTimeSlot, String tableNumber) {
        int timeSlotId = getTimeSlotIdByTimeSlot(selectedTimeSlot);
        int userId = getUserIdByUserName(username);

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(INSERT_RESERVATION_QUERY)) {
            insertStmt.setInt(1, userId);
            insertStmt.setInt(2, restaurantId);
            insertStmt.setDate(3, java.sql.Date.valueOf(reservationDate));
            insertStmt.setInt(4, timeSlotId);
            insertStmt.setString(5, tableNumber);
            return insertStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int getTimeSlotIdByTimeSlot(String selectedTimeSlot) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement prepare = conn.prepareStatement(SELECT_TIME_SLOT_ID_QUERY)) {
            prepare.setString(1, selectedTimeSlot);
            ResultSet resultSet = prepare.executeQuery();
            return resultSet.next() ? resultSet.getInt("slot_id") : -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private int getUserIdByUserName(String username) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement prepare = conn.prepareStatement(SELECT_USER_ID_QUERY)) {
            prepare.setString(1, username);
            ResultSet resultSet = prepare.executeQuery();
            return resultSet.next() ? resultSet.getInt("user_id") : -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<String> getAvailableTimeSlots(Restaurant restaurant, LocalDate selectedDate, int selectedGuests, Table table) {
        List<String> availableSlots = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AVAILABLE_TIME_SLOTS_QUERY)) {
            preparedStatement.setInt(1, restaurant.getRestaurantId());
            preparedStatement.setDate(2, Date.valueOf(selectedDate));
            preparedStatement.setInt(3, selectedGuests);
            preparedStatement.setInt(4, table.getNumberOfSeats());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String slotLabel = resultSet.getString("slot_label");
                availableSlots.add(slotLabel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableSlots;
    }

    private Table mapResultSetToTable(ResultSet resultSet, int restaurantId) throws SQLException {
        Table table = new Table();
        table.setTableId(resultSet.getInt("table_id"));
        table.setRestaurantId(restaurantId);
        table.setTableNumber(resultSet.getString("table_number"));
        table.setNumberOfSeats(resultSet.getInt("number_of_seats"));
        table.setBookingFee(resultSet.getDouble("booking_fee"));
        return table;
    }
}