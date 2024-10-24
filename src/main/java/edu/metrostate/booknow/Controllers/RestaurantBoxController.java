package edu.metrostate.booknow.Controllers;

import edu.metrostate.booknow.Models.Restaurant;
import edu.metrostate.booknow.Services.RestaurantServices;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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

    private final RestaurantServices restaurantServices;

    public RestaurantBoxController() {
        this.restaurantServices = new RestaurantServices(); // Using Services to handle business logic
    }

    // Sets the restaurant data into the UI elements of the RestaurantBox
    public void setRestaurantData(Restaurant restaurant) {
        setLabels(restaurant);

        // Fetch and display the average rating
        String averageRating = restaurantServices.getAverageRating(restaurant.getRestaurantId());
        ratingLabel.setText("Rating: " + averageRating + "/5");

        // Load and display restaurant image
        displayRestaurantImage(restaurant.getImagePath(), restaurantImageView);
    }

    // set labels for restaurant details
    private void setLabels(Restaurant restaurant) {
        restaurantNameLabel.setText(restaurant.getName());
        restaurantLocationLabel.setText(restaurant.getCity());
        restaurantDescriptionLabel.setText(restaurant.getDescription());
    }

    // Method to load and display the restaurant image
    private void displayRestaurantImage(String imagePath, ImageView imageView) {
        if (imagePath == null || imagePath.isEmpty()) {
            System.err.println("Image path is null or empty");
            return;
        }

        try {
            Image restaurantImage = new Image(getClass().getResource("/" + imagePath).toExternalForm());
            imageView.setImage(restaurantImage);
        } catch (NullPointerException e) {
            System.err.println("Image not found: " + imagePath);
        }
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
