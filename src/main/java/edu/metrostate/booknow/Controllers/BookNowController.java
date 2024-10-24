package edu.metrostate.booknow.Controllers;

import edu.metrostate.booknow.Models.Restaurant;
import edu.metrostate.booknow.Services.RestaurantServices;
import edu.metrostate.booknow.Services.UserServices;
import edu.metrostate.booknow.Utils.AlertUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

/**
 * The BookNowController handles the UI interaction logic for the restaurant booking system.
 * It interacts with various UI components such as labels, combo boxes, date pickers, and VBoxes.
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

    private final RestaurantServices restaurantServices;

    public BookNowController() {
        this.restaurantServices = new RestaurantServices();
    }

    @FXML
    public void initialize() {
        // Set welcome message for logged-in user
        lbl_welcome.setText("Welcome, " + UserServices.getCurrentUser().getUserName());

        // Populate the location and cuisine type combo boxes
        locationComboBox.getItems().addAll(restaurantServices.getCityNames());
        cb_cuisineType.getItems().addAll(restaurantServices.getCuisineTypes());

        // Populate the adults and children combo boxes
        cb_adults.setItems(FXCollections.observableArrayList(IntStream.rangeClosed(1, 99).boxed().toList()));
        cb_children.setItems(FXCollections.observableArrayList(IntStream.rangeClosed(0, 99).boxed().toList()));
    }

    @FXML
    public void onSearchButtonClick(ActionEvent event) {
        String selectedCity = locationComboBox.getSelectionModel().getSelectedItem();
        String selectedCuisineType = cb_cuisineType.getSelectionModel().getSelectedItem();
        LocalDate selectedDate = checkInDate.getValue();

        // validate, if invalid, an alert is shown and the method exits.
        if (!restaurantServices.isSearchCriteriaValid(selectedCity, selectedCuisineType, selectedDate)) {
            AlertUtil.showInfoAlert("Invalid Search Criteria", restaurantServices.getValidationMessage());
            return;
        }

        // If valid, available restaurants are queried and then displayed to the user.
        List<Restaurant> restaurants = restaurantServices.findAvailableRestaurants(selectedCity, selectedCuisineType, selectedDate);
        populateRestaurants(restaurants);
    }

    // Populate restaurants in the VBox
    private void populateRestaurants(List<Restaurant> restaurants) {
        restaurantVBox.getChildren().clear(); // Clear previous results

        if (restaurants.isEmpty()) {
            AlertUtil.showInfoAlert("No Availability", "No restaurants are available for the selected date and criteria. Please search again.");
        } else {
            for (Restaurant restaurant : restaurants) {
                addRestaurantToVBox(restaurant);
            }
        }
    }

    // Dynamically load restaurant data into VBox
    private void addRestaurantToVBox(Restaurant restaurant) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/metrostate/booknow/RestaurantBox.fxml"));
            VBox restaurantBox = loader.load();

            // Set restaurant data in the RestaurantBoxController
            RestaurantBoxController controller = loader.getController();
            controller.setRestaurantData(restaurant);

            restaurantVBox.getChildren().add(restaurantBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
