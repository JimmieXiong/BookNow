package edu.metrostate.booknow.Controllers;

import edu.metrostate.booknow.Models.Restaurant;
import edu.metrostate.booknow.Models.Review;
import edu.metrostate.booknow.Models.Table;
import edu.metrostate.booknow.Services.BookNowFacadeService;
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

    private final BookNowFacadeService serviceManager;

    public BookNowController() {
        this.serviceManager = new BookNowFacadeService(new DBConnection());
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
        serviceManager.searchRestaurants(selectedCity, selectedCuisineType, selectedAdults, selectedChildren, selectedDate, restaurantListVBox, this);
    }

    public void handleShowAvailability(Restaurant restaurant) {selectedDate = checkInDate.getValue();
        totalGuests = cb_adults.getSelectionModel().getSelectedItem() + cb_children.getSelectionModel().getSelectedItem();
        serviceManager.showAvailability(restaurant, selectedDate, totalGuests, availabilityVBox, restaurantListVBox, reviewsOverlay, this);
    }

    // Ran into errors where views were showing, not showing, showing in the background, etc
    // fix by setting. Show the availability view and hide other views
    public void showAvailabilityView() {
        // Hide the restaurant list view
        restaurantListVBox.setVisible(false);
        // Show the availability view without will not show
        availabilityVBox.setVisible(true);
        availabilityVBox.setManaged(true);
    }

    public void showRestaurantListView() {
        // Hide the availability view without when hitting back button error in background for some reason not clearing
        availabilityVBox.setVisible(false);
        // Hide the reviews overlay when searching new restaurants
        reviewsOverlay.setVisible(false);
        // Show the restaurant list view without will not show
        restaurantListVBox.setVisible(true);
        restaurantListVBox.setManaged(true);
    }

    public void handleReserveTable(Restaurant restaurant, Table table) {
        boolean success = serviceManager.reserveTable(UIUtil.USER, restaurant.getRestaurantId(), selectedDate, selectedTimeSlot, table.getTableNumber());
        UIUtil.displayAlert(success ? "Reservation Confirmed" : "Reservation Failed",
                success ? "Your reservation is confirmed." : "Failed to reserve the table. Please try again.");
    }

    public void handleViewMenu(Restaurant restaurant) {
        serviceManager.getRestaurantUIManager().viewMenu(restaurant);
    }

    public void handleReadReviews(Restaurant restaurant) {
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
    public void setSelectedTimeSlot(String selectedTimeSlot) {
        this.selectedTimeSlot = selectedTimeSlot;
    }

    public void onViewMyReviewsClick(ActionEvent event) {
        UIUtil.displayScene(getClass().getResource("/edu/metrostate/booknow/ReviewView.fxml"), event);
    }
}
