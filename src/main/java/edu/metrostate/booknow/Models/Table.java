package edu.metrostate.booknow.Models;

public class Table {
    private int tableId;
    private int restaurantId;
    private String tableNumber;
    private int numberOfSeats;
    private double bookingFee;

    private Restaurant restaurant;

    public void setTableId(int tableId) {
        this.tableId = tableId;
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
    /*
     * Stack Trace Analysis:
     *
     * Method calls and their origins:
     *
     * - getBookingFee:58, Table (edu.metrostate.booknow.Models)
     *   78 hidden frames
     *   - Async stack trace
     *     5 hidden frames
     *
     * Overview of the chain:
     * The chain of method calls indicates that somewhere in an asynchronous context, getBookingFee() is called on a Table instance.
     * The presence of hidden frames suggests that there are several internal method calls or framework-related calls that are not immediately relevant.
     */
    public double getBookingFee() {
        return bookingFee;
    }

    public void setBookingFee(double bookingFee) {
        this.bookingFee = bookingFee;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
