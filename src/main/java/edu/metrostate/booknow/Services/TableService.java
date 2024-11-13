package edu.metrostate.booknow.Services;

import edu.metrostate.booknow.DAO.TableDAO;
import edu.metrostate.booknow.Models.Restaurant;
import edu.metrostate.booknow.Models.Table;

import java.time.LocalDate;
import java.util.List;

public class TableService {
    private final TableDAO tableDAO;

    public TableService(TableDAO tableDAO) {
        this.tableDAO = tableDAO;
    }

    public List<Table> getAvailableTables(int restaurantId, LocalDate date, int numOfGuests) {
        return tableDAO.getAvailableTables(restaurantId, date, numOfGuests);
    }

    public boolean reserveTable(String username, int restaurantId, LocalDate reservationDate, String selectedTimeSlot, String tableNumber) {
        return tableDAO.reserveTable(username, restaurantId, reservationDate, selectedTimeSlot, tableNumber);
    }

    public List<String> getAvailableTimeSlots(Restaurant restaurant, LocalDate selectedDate, int selectedGuests, Table table) {
        return tableDAO.getAvailableTimeSlots(restaurant, selectedDate, selectedGuests, table);
    }
}
