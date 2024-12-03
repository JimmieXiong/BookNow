package edu.metrostate.booknow.Controllers;

import edu.metrostate.booknow.DAO.ReviewDAO;
import edu.metrostate.booknow.Utils.DBConnection;
import edu.metrostate.booknow.Models.Review;
import edu.metrostate.booknow.Services.ReviewService;
import edu.metrostate.booknow.Utils.UIUtil;
import edu.metrostate.booknow.Utils.ReviewUIManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReviewController {
    @FXML
    private Label lbl_welcome;
    @FXML
    private TableView<Review> reviewsTable;
    @FXML
    private TableColumn<Review, String> restaurantNameColumn;
    @FXML
    private TableColumn<Review, String> dateOfExperienceColumn;
    @FXML
    private TableColumn<Review, String> ratingColumn;
    @FXML
    private TableColumn<Review, String> feedbackColumn;

    private final ReviewUIManager reviewUIManager;

    public ReviewController() {
        this.reviewUIManager = new ReviewUIManager();
    }

    @FXML
    public void initialize() {
        lbl_welcome.setText("Welcome, " + UIUtil.USER);
        reviewUIManager.setUpTableColumns(restaurantNameColumn, dateOfExperienceColumn, ratingColumn, feedbackColumn);
        loadReviews();
    }

    private void loadReviews() {
        ObservableList<Review> reviewList = reviewUIManager.loadReviews(UIUtil.USER);
        reviewsTable.setItems(reviewList);
    }

    public void onSearchRestaurantsClick(ActionEvent event) {
        UIUtil.displayScene(getClass().getResource("/edu/metrostate/booknow/BookNowView.fxml"), event);
    }
}
