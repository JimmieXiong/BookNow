package edu.metrostate.booknow.Models;

import javafx.scene.control.Button;

import java.time.LocalDate;

public class Reservation {

    private int reservationId;
    private int restaurantId;
    private String restaurantName;
    private LocalDate reservationDate;
    private String timeSlot;
    private String tableNumber;
    private Button actionButton;

    private User user;
    private Restaurant restaurant;
    private TimeSlot timeSlotReserved;
    private Table table;

    public Reservation(int reservationId, int restaurantId, String restaurantName, LocalDate reservationDate, String timeSlot, String tableNumber) {
        this.reservationId = reservationId;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.reservationDate = reservationDate;
        this.timeSlot = timeSlot;
        this.tableNumber = tableNumber;
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Button getActionButton() {
        return actionButton;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public TimeSlot getTimeSlotReserved() {
        return timeSlotReserved;
    }

    public void setTimeSlotReserved(TimeSlot timeSlotReserved) {
        this.timeSlotReserved = timeSlotReserved;
    }

    public void setActionButton(Button actionButton) {
        this.actionButton = actionButton;
    }
}
