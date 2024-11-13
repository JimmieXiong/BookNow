package edu.metrostate.booknow.Controllers;

import edu.metrostate.booknow.Models.Restaurant;
import edu.metrostate.booknow.Models.Review;
import edu.metrostate.booknow.Models.Table;
import edu.metrostate.booknow.Services.BookNowServiceManager;
import edu.metrostate.booknow.Utils.DBConnection;
import edu.metrostate.booknow.Utils.UIUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

public class BookNowController {

    @FXML
    private Label lbl_welcome;
    @FXML
    private ComboBox<String> locationComboBox;
    @FXML
    private ComboBox<String> cb_cuisineType;
    @FXML
    private ComboBox<Integer> cb_adults;
    @FXML
    private ComboBox<Integer> cb_children;
    @FXML
    private DatePicker checkInDate;
    @FXML
    private VBox restaurantListVBox;
    @FXML
    private VBox reviewsOverlay;
    @FXML
    private VBox availabilityVBox;

    private String selectedCity;
    private String selectedCuisineType;
    private LocalDate selectedDate;
    private int totalGuests;
    private String selectedTimeSlot;

    private final BookNowServiceManager serviceManager;

    public BookNowController() {
        this.serviceManager = new BookNowServiceManager(new DBConnection());
    }

    @FXML
    public void initialize() {
        lbl_welcome.setText("Welcome, " + UIUtil.USER);
        UIUtil.populateComboBox(locationComboBox, serviceManager.getCityNames());
        UIUtil.populateComboBox(cb_cuisineType, serviceManager.getCuisineTypes());
        UIUtil.populateComboBox(cb_adults, IntStream.rangeClosed(1, 99).boxed().toList());
        UIUtil.populateComboBox(cb_children, IntStream.rangeClosed(0, 99).boxed().toList());
    }

    public void onSearchButtonClick(ActionEvent event) {
        handleBackToRestaurants();

        selectedCity = locationComboBox.getSelectionModel().getSelectedItem();
        selectedCuisineType = cb_cuisineType.getSelectionModel().getSelectedItem();
        Integer selectedAdults = cb_adults.getSelectionModel().getSelectedItem();
        Integer selectedChildren = cb_children.getSelectionModel().getSelectedItem();
        selectedDate = checkInDate.getValue();

        if (serviceManager.validateSearchInputs(selectedCity, selectedCuisineType, selectedAdults, selectedChildren, selectedDate)) {
            totalGuests = selectedAdults + selectedChildren;

            List<Restaurant> restaurants = serviceManager.getAvailableRestaurants(selectedCity, selectedCuisineType, totalGuests, selectedDate);
            serviceManager.getRestaurantUIManager().populateRestaurantListVBox(
                    restaurantListVBox, restaurants, this::handleReadReviews, this::handleViewMenu, this::handleShowAvailability
            );
        }
    }

    private void handleShowAvailability(Restaurant restaurant) {
        showAvailabilityView();
        availabilityVBox.getChildren().clear();

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> showRestaurantListView());
        availabilityVBox.getChildren().add(backButton);

        Label availabilityLabel = new Label("Available Tables for " + restaurant.getName());
        availabilityLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #003580; -fx-font-weight: bold;");
        availabilityVBox.getChildren().add(availabilityLabel);

        selectedDate = checkInDate.getValue();
        totalGuests = cb_adults.getSelectionModel().getSelectedItem() + cb_children.getSelectionModel().getSelectedItem();

        TableView<Table> tableView = serviceManager.getRestaurantUIManager().createTableView(
                restaurant, selectedDate, totalGuests, table -> handleReserveTable(restaurant, table),
                selectedTimeSlot -> this.selectedTimeSlot = selectedTimeSlot
        );

        availabilityVBox.getChildren().add(tableView);
        reviewsOverlay.setVisible(false);
        reviewsOverlay.setManaged(false);
    }

    private void showAvailabilityView() {
        restaurantListVBox.setVisible(false);
        restaurantListVBox.setManaged(false);
        reviewsOverlay.setVisible(false);
        reviewsOverlay.setManaged(false);
        availabilityVBox.setVisible(true);
        availabilityVBox.setManaged(true);
    }

    private void showRestaurantListView() {
        availabilityVBox.setVisible(false);
        availabilityVBox.setManaged(false);
        reviewsOverlay.setVisible(false);
        reviewsOverlay.setManaged(false);
        restaurantListVBox.setVisible(true);
        restaurantListVBox.setManaged(true);
    }

    private void handleReserveTable(Restaurant restaurant, Table table) {
        boolean success = serviceManager.reserveTable(UIUtil.USER, restaurant.getRestaurantId(), selectedDate, selectedTimeSlot, table.getTableNumber());
        UIUtil.displayAlert(success ? "Reservation Confirmed" : "Reservation Failed",
                success ? "Your reservation is confirmed." : "Failed to reserve the table. Please try again.");
    }

    private void handleViewMenu(Restaurant restaurant) {
        serviceManager.getRestaurantUIManager().viewMenu(restaurant);
    }

    private void handleReadReviews(Restaurant restaurant) {
        List<Review> reviews = serviceManager.getReviewsByRestaurantId(restaurant.getRestaurantId());
        reviewsOverlay.setVisible(true);
        reviewsOverlay.setManaged(true);
        serviceManager.getRestaurantUIManager().displayReviews(reviewsOverlay, reviews);
    }

    public void handleBackToRestaurants() {
        showRestaurantListView();
    }

    public void onViewMyReservationsClick(ActionEvent event) {
        UIUtil.displayScene(getClass().getResource("/edu/metrostate/booknow/ReservationsView.fxml"), event);
    }

    public void onViewMyReviewsClick(ActionEvent event) {
        UIUtil.displayScene(getClass().getResource("/edu/metrostate/booknow/ReviewView.fxml"), event);
    }
}
