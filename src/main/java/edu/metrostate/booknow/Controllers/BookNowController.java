package edu.metrostate.booknow.Controllers;

import edu.metrostate.booknow.Models.Restaurant;
import edu.metrostate.booknow.Services.RestaurantService;
import edu.metrostate.booknow.Services.UserServices;
import edu.metrostate.booknow.Utils.UIUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

/**
 * The BookNowController class is responsible for managing the user interface interactions
 * related to booking restaurants. It includes methods to initialize the interface, handle
 * search actions, and populate the results based on user selections.
 */

public class BookNowController {

    @FXML
    private Label lbl_welcome;
    @FXML
    private ComboBox<String> locationComboBox;
    @FXML
    private ComboBox<String> cb_cuisineType;
    @FXML
    private DatePicker checkInDate;
    @FXML
    private VBox restaurantVBox;
    @FXML
    private ComboBox<Integer> cb_adults;
    @FXML
    private ComboBox<Integer> cb_children;

    private final RestaurantService restaurantService;

    public BookNowController() {
        this.restaurantService = new RestaurantService();
    }

    /**
     * Initializes the BookNowController by setting up the welcome message and populating
     * various ComboBox elements with appropriate data.
     *
     * This method performs the following actions:
     * - Sets the welcome message for the currently logged-in user.
     * - Populates the locationComboBox with city names fetched from the restaurant service.
     * - Populates the cb_cuisineType ComboBox with cuisine types fetched from the restaurant service.
     * - Populates the cb_adults ComboBox with a range of numbers from 1 to 99.
     * - Populates the cb_children ComboBox with a range of numbers from 0 to 99.
     */
    @FXML
    public void initialize() {
        // Set welcome message for logged-in user
        setWelcomeMessage();
        // Populate the combo boxes
        UIUtils.populateComboBox(locationComboBox, restaurantService.fetchCityNames());
        UIUtils.populateComboBox(cb_cuisineType, restaurantService.fetchCuisineTypes());
        UIUtils.populateComboBox(cb_adults, IntStream.rangeClosed(1, 99).boxed().toList());
        UIUtils.populateComboBox(cb_children, IntStream.rangeClosed(0, 99).boxed().toList());
    }

    /**
     * Sets the welcome message for the currently logged-in user.
     * The welcome message is displayed via the 'lbl_welcome' label and includes the current user's name.
     */
    private void setWelcomeMessage() {
        lbl_welcome.setText("Welcome, " + UserServices.getCurrentUser().getUserName());
    }

    /**
     * Handles the search button click event. This method retrieves the selected city,
     * cuisine type, and check-in date from the UI components and validates the search criteria.
     * If the criteria are valid, it queries for available restaurants and displays the results.
     * Otherwise, it shows an error alert.
     *
     * @param event the ActionEvent that triggered this method
     */
    @FXML
    public void onSearchButtonClick(ActionEvent event) {
        String selectedCity = locationComboBox.getSelectionModel().getSelectedItem();
        String selectedCuisineType = cb_cuisineType.getSelectionModel().getSelectedItem();
        LocalDate selectedDate = checkInDate.getValue();

        // Validate, if invalid, an alert is shown and the method exits.
        if (!restaurantService.validateSearchCriteria(selectedCity, selectedCuisineType, selectedDate)) {
            UIUtils.showErrorAlert("Invalid Search Criteria", restaurantService.getValidationMessage());
            return;
        }

        // If valid, available restaurants are queried and then displayed to the user.
        List<Restaurant> restaurants = restaurantService.findAvailableRestaurants(selectedCity, selectedCuisineType, selectedDate);
        populateRestaurants(restaurants);
    }

    /**
     * Populates the provided list of restaurants into the VBox. Clears any existing
     * children in the VBox before adding the new restaurant entries. If the list of
     * restaurants is empty, an informational alert is shown to the user.
     *
     * @param restaurants A list of Restaurant objects to be displayed.
     */
    // Populate restaurants in the VBox
    private void populateRestaurants(List<Restaurant> restaurants) {
        restaurantVBox.getChildren().clear(); // Clear previous results
        if (restaurants.isEmpty()) {
            UIUtils.showInfoAlert("No Availability", "No restaurants are available for the selected date and criteria. Please search again.");
        } else {
            for (Restaurant restaurant : restaurants) {
                VBox restaurantBox = UIUtils.createRestaurantBox(restaurant);
                if (restaurantBox != null) {
                    restaurantVBox.getChildren().add(restaurantBox);
                } else {
                    UIUtils.showInfoAlert("Error", "Failed to load restaurant details.");
                }
            }
        }
    }
}
