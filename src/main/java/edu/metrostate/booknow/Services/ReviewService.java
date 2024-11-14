package edu.metrostate.booknow.Services;

import edu.metrostate.booknow.DAO.ReviewDAO;
import edu.metrostate.booknow.Models.Review;

import java.time.LocalDate;
import java.util.List;

public class ReviewService {
    private final ReviewDAO reviewDAO;

    public ReviewService(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    public String getAverageRating(int restaurantId) {
        return reviewDAO.getAverageRating(restaurantId);
    }

    public List<Review> getReviewsByRestaurantId(int restaurantId) {
        return reviewDAO.getReviewsByRestaurantId(restaurantId);
    }

    public List<Review> getReviewsByUsername(String username) {
        return reviewDAO.getReviewsByUsername(username);
    }

    public void submitReview(String username, int restaurantId, int reservationId, int rating, String feedback, LocalDate dateOfExperience) {
        reviewDAO.submitReview(username, restaurantId, reservationId, rating, feedback, dateOfExperience);
    }

    // New method to handle validation and submission
    public String validateAndSubmitReview(String username, int restaurantId, int reservationId, Integer rating, String feedback, LocalDate dateOfExperience) {
        if (rating == null) {
            return "Please select a rating.";
        }

        if (feedback == null || feedback.isBlank()) {
            return "Please provide a review.";
        }

        submitReview(username, restaurantId, reservationId, rating, feedback, dateOfExperience);
        return "Success";
    }
}