package edu.metrostate.booknow.Controllers;

import edu.metrostate.booknow.Models.Restaurant;
import edu.metrostate.booknow.Services.RestaurantService;
import edu.metrostate.booknow.Utils.UIUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * The RestaurantBoxController class is responsible for managing the UI components
 * that display information about a restaurant. It interacts with RestaurantServices
 * to set and display various restaurant details.
 */
public class RestaurantBoxController {

    @FXML
    private Label restaurantNameLabel;
    @FXML
    private Label ratingLabel;
    @FXML
    private Label restaurantLocationLabel;
    @FXML
    private Label restaurantDescriptionLabel;
    @FXML
    private ImageView restaurantImageView;

    private final RestaurantService restaurantService;

    public RestaurantBoxController() {
        this.restaurantService = new RestaurantService(); // Using Services to handle business logic
    }

    /**
     * Sets the restaurant data into the UI elements of the RestaurantBox.
     *
     * @param restaurant the Restaurant object containing the data to display
     */
    // Sets the restaurant data into the UI elements of the RestaurantBox
    public void setRestaurantData(Restaurant restaurant) {
        updateLabels(restaurant);
        updateRating(restaurant.getRestaurantId());
        UIUtils.displayImage(restaurant.getImagePath(), restaurantImageView);
    }

    /**
     * Updates the labels in the UI with the restaurant's details.
     *
     * @param restaurant the Restaurant object containing details to be displayed
     */
    // Set labels for restaurant details
    private void updateLabels(Restaurant restaurant) {
        restaurantNameLabel.setText(restaurant.getName());
        restaurantLocationLabel.setText(restaurant.getCity());
        restaurantDescriptionLabel.setText(restaurant.getDescription());
    }

    /**
     * Fetches and displays the average rating of a restaurant.
     *
     * @param restaurantId the unique identifier of the restaurant for which the average rating is to be fetched
     */
    // Fetch and display the average rating
    private void updateRating(int restaurantId) {
        String averageRating = restaurantService.fetchAverageRating(restaurantId);
        ratingLabel.setText("Rating: " + averageRating + "/5");
    }

    @FXML
    public void reviewsButton() {
        // Logic for handling reviews
    }

    @FXML
    public void menuButton() {
        // Logic for handling menu
    }

    @FXML
    public void availabilityButton() {
        // Logic for handling availability
    }
}