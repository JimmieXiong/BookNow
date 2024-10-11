package edu.metrostate.booknow.Controllers;

import edu.metrostate.booknow.DBConnection;
import edu.metrostate.booknow.Models.Restaurant;
import edu.metrostate.booknow.Util;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

public class BookNowController {

    @FXML
    Label lbl_welcome;
    @FXML
    ComboBox<String> locationComboBox;
    @FXML
    ComboBox<String> cb_cuisineType;
    @FXML
    DatePicker checkInDate;
    @FXML
    private VBox restaurantListVBox;
    @FXML
    ComboBox<Integer> cb_adults;
    @FXML
    ComboBox<Integer> cb_children;

    private String selectedCity;
    private String selectedCuisineType;
    private LocalDate selectedDate;

    private DBConnection dbHandler;

    public BookNowController() {
        System.out.println("BookNowController constructor called");
        dbHandler = new DBConnection();
    }

    @FXML
    public void initialize() {
        // Set the welcome label with the current user's name
        lbl_welcome.setText("Welcome, " + Util.getCurrentUser());

        // Populate the location combo box with city names fetched from the database
        List<String> cities = dbHandler.fetchCityNames();
        locationComboBox.getItems().addAll(cities);

        List<String> cuisineTypes = dbHandler.fetchCuisineTypes();
        cb_cuisineType.getItems().addAll(cuisineTypes);

        // Populate adults combo box (1 to 99)
        List<Integer> adults = IntStream.rangeClosed(1, 99).boxed().toList();
        cb_adults.setItems(FXCollections.observableArrayList(adults));

        // Populate children combo box (0 to 99)
        List<Integer> children = IntStream.rangeClosed(0, 99).boxed().toList();
        cb_children.setItems(FXCollections.observableArrayList(children));
    }

    public void onSearchButtonClick(ActionEvent event) {
        selectedCity = locationComboBox.getSelectionModel().getSelectedItem();
        selectedCuisineType = cb_cuisineType.getSelectionModel().getSelectedItem();
        selectedDate = checkInDate.getValue();

        // Validate inputs
        if (selectedCity == null) {
            Util.displayAlert("Location Selection", "Please select a location.");
            return;
        }
        if (selectedCuisineType == null) {
            Util.displayAlert("Cuisine Type Selection", "Please select a cuisine type.");
            return;
        }
        if (selectedDate == null) {
            Util.displayAlert("Date Selection", "Please select a date.");
            return;
        }
        if (selectedDate.isBefore(LocalDate.now())) {
            Util.displayAlert("Invalid Date", "Check-in date cannot be in the past.");
            return;
        }

        // Fetch available restaurants based on search criteria
        List<Restaurant> restaurants = dbHandler.getAvailableRestaurants(selectedCity, selectedCuisineType, selectedDate);
        populateRestaurantListVBox(restaurants);
    }

    /**
     * Populates the VBox with a list of restaurants, creating separate VBox elements for each restaurant
     * and adding them to the main restaurant list VBox.
     *
     * @param restaurants the list of restaurants to add to the VBox
     */
    private void populateRestaurantListVBox(List<Restaurant> restaurants) {
        restaurantListVBox.getChildren().clear();
        for (Restaurant restaurant : restaurants) {
            VBox restaurantBox = new VBox();
            restaurantBox.setStyle("-fx-spacing: 10; -fx-border-color: lightgray; -fx-padding: 10;");

            Label nameLabel = new Label(restaurant.getName());
            nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #003580;");
            Label locationLabel = new Label(restaurant.getCity());
            locationLabel.setStyle("-fx-text-fill: gray;");
            Label descriptionLabel = new Label(restaurant.getDescription());

            restaurantBox.getChildren().addAll(nameLabel, locationLabel, descriptionLabel);
            restaurantListVBox.getChildren().add(restaurantBox);
        }
    }
}
