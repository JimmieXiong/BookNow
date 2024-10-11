package edu.metrostate.booknow.Models;

public class Table {
    private int tableId;
    private int restaurantId;
    private String tableNumber;
    private int numberOfSeats;
    private double bookingFee;
    private boolean isAvailable;

    private Restaurant restaurant;

    public Table() {
    }

    public Table(int tableId, int restaurantId, String tableNumber, int numberOfSeats, double bookingFee, boolean isAvailable) {
        this.tableId = tableId;
        this.restaurantId = restaurantId;
        this.tableNumber = tableNumber;
        this.numberOfSeats = numberOfSeats;
        this.bookingFee = bookingFee;
        this.isAvailable = isAvailable;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public double getBookingFee() {
        return bookingFee;
    }

    public void setBookingFee(double bookingFee) {
        this.bookingFee = bookingFee;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
