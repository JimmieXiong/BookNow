package edu.metrostate.booknow.Models;

import java.time.LocalDate;

public class Review {
    private String username;
    private String restaurantName;
    private int rating;
    private String feedback;
    private LocalDate dateOfExperience;

    private User user;
    private Restaurant restaurant;

    public Review(String username, String restaurantName, int rating, String feedback, LocalDate dateOfExperience) {
        this.username = username;
        this.restaurantName = restaurantName;
        this.rating = rating;
        this.feedback = feedback;
        this.dateOfExperience = dateOfExperience;
    }

    public String getUsername() {
        return username;
    }
    /*
     * Stack Trace Analysis:
     *
     * Method calls and their origins:
     *
     * - getRestaurantName:36, Review (edu.metrostate.booknow.Models)
     *   119 hidden frames
     * - setScene:27, UIUtil (edu.metrostate.booknow.Utils)
     * - displayScene:42, UIUtil (edu.metrostate.booknow.Utils)
     * - onViewMyReviewsClick:125, BookNowController (edu.metrostate.booknow.Controllers)
     *   68 hidden frames
     *
     * Overview of the chain:
     * The chain of method calls starts in onViewMyReviewsClick in BookNowController.
     * onViewMyReviewsClick calls displayScene in UIUtil, which then calls setScene.
     * setScene eventually leads to a situation where getRestaurantName() is called on a Review instance.
     */
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

    public void setUsername(String username) {
        this.username = username;
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

}
