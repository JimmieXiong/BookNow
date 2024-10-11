package edu.metrostate.booknow.Models;

import java.time.LocalDate;

public class Review {
    private int reviewId;
    private String username;
    private String restaurantName;
    private int rating;
    private String feedback;
    private LocalDate dateOfExperience;

    private User user;
    private Restaurant restaurant;
    private TimeSlot timeSlotReserved;
    private Reservation reservation;

    public Review(int reviewId, String username, String restaurantName, int rating, String feedback, LocalDate dateOfExperience) {
        this.reviewId = reviewId;
        this.username = username;
        this.restaurantName = restaurantName;
        this.rating = rating;
        this.feedback = feedback;
        this.dateOfExperience = dateOfExperience;
    }

    public int getReviewId() {
        return reviewId;
    }

    public String getUsername() {
        return username;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public int getRating() {
        return rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public LocalDate getDateOfExperience() {
        return dateOfExperience;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setDateOfExperience(LocalDate dateOfExperience) {
        this.dateOfExperience = dateOfExperience;
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

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
