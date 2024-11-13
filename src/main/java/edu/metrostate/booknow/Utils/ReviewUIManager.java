package edu.metrostate.booknow.Utils;

import edu.metrostate.booknow.Models.Review;
import edu.metrostate.booknow.DAO.ReviewDAO;
import edu.metrostate.booknow.Services.ReviewService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ReviewUIManager {
    private final ReviewService reviewService;

    public ReviewUIManager() {
        this.reviewService = new ReviewService(new ReviewDAO(new DBConnection()));
    }

    public void setUpTableColumns(TableColumn<Review, String> restaurantNameColumn, TableColumn<Review, String> dateOfExperienceColumn, TableColumn<Review, String> ratingColumn, TableColumn<Review, String> feedbackColumn) {
        restaurantNameColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));
        dateOfExperienceColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfExperience"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        feedbackColumn.setCellValueFactory(new PropertyValueFactory<>("feedback"));
    }

    public ObservableList<Review> loadReviews(String username) {
        List<Review> reviews = reviewService.getReviewsByUsername(username);
        return FXCollections.observableArrayList(reviews);
    }
}
