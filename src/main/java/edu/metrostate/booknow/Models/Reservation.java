package edu.metrostate.booknow.Models;

import javafx.scene.control.Button;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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

    /*
    hidden frames
     * The chain of method calls started in onViewMyReservationsClick in BookNowController.
     * onViewMyReservationsClick calls displayScene in UIUtil, which then calls setScene.
     * setScene eventually leads to a situation where getTimeSlot() is called on a Reservation instance.
     * ex: timeSlot: "9:00 AM - 11:00 AM"
     */
    public String getTimeSlot() {
        return timeSlot;
    }
    /*
     * Stack Trace Analysis:
     * Overview of the chain:
     * The chain of method calls starts in onViewMyReservationsClick in BookNowController.
     * onViewMyReservationsClick calls displayScene in UIUtil, which then calls setScene.
     * setScene eventually leads to a situation where getTableNumber() is called on a Reservation instance.
     * ex: tableNumber = "T1"
     */
    public String getTableNumber() {
        return tableNumber;
    }

    /*
     * Stack Trace Analysis:
     *
     * Method calls and their origins:
     *
     * - getActionButton:83, Reservation (edu.metrostate.booknow.Models)
     *   119 hidden frames
     * - setScene:27, UIUtil (edu.metrostate.booknow.Utils)
     * - displayScene:42, UIUtil (edu.metrostate.booknow.Utils)
     * - onViewMyReservationsClick:121, BookNowController (edu.metrostate.booknow.Controllers)
     *   68 hidden frames
     *
     * Overview of the chain:
     * The chain of method calls starts in onViewMyReservationsClick in BookNowController.
     * onViewMyReservationsClick calls displayScene in UIUtil, which then calls setScene.
     * setScene eventually leads to a situation where getActionButton() is called on a Reservation instance.
     */
    //Button@52dadd04[styleClass=button]'View Your Review'
    public Button getActionButton() {
        return actionButton;
    }

    public int getRestaurantId() {
        return restaurantId;
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

    public void setActionButton(Button actionButton) {
        this.actionButton = actionButton;
    }


    public boolean checkReservationDateTimePassed() {
        LocalTime reservationStartTime = parseStartTime();
        LocalDateTime reservationDateTime = LocalDateTime.of(reservationDate, reservationStartTime);
        LocalDateTime currentDateTime = LocalDateTime.now();
        return reservationDateTime.isBefore(currentDateTime);
    }

    private LocalTime parseStartTime() {
        String[] timeRange = timeSlot.split(" - ");
        String startTime = timeRange[0];
        return LocalTime.parse(startTime, DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH));
    }

}
