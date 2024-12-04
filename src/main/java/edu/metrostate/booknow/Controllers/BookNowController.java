package edu.metrostate.booknow.Controllers;

import edu.metrostate.booknow.Models.Restaurant;
import edu.metrostate.booknow.Models.Table;
import edu.metrostate.booknow.Services.BookNowFacadeService;
import edu.metrostate.booknow.Utils.DBConnection;
import edu.metrostate.booknow.Utils.UIUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.time.LocalDate;
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

    private final BookNowFacadeService BookNowFacadeService;

    public BookNowController() {
        this.BookNowFacadeService = new BookNowFacadeService(new DBConnection());
    }

    @FXML
    public void initialize() {
        lbl_welcome.setText("Welcome, " + UIUtil.USER);

        // Populate the cities, and cuisine types into the combobox elements
        UIUtil.populateComboBox(locationComboBox, BookNowFacadeService.getCityNames());
        UIUtil.populateComboBox(cb_cuisineType, BookNowFacadeService.getCuisineTypes());

        // Populate 1,10, 0,10
        UIUtil.populateComboBox(cb_adults, IntStream.rangeClosed(1, 10).boxed().toList());
        UIUtil.populateComboBox(cb_children, IntStream.rangeClosed(0, 10).boxed().toList());

        // Ensure prompt text is displayed without selecting any item initially
        cb_adults.getSelectionModel().clearSelection();
        cb_adults.setPromptText("Adults");

        cb_children.getSelectionModel().clearSelection();
        cb_children.setPromptText("Children");
    }

    public void onSearchButtonClick(ActionEvent event) {
        showRestaurantListView();
        // Retrieve user selections
        selectedCity = locationComboBox.getSelectionModel().getSelectedItem();
        selectedCuisineType = cb_cuisineType.getSelectionModel().getSelectedItem();
        Integer selectedAdults = cb_adults.getSelectionModel().getSelectedItem();
        Integer selectedChildren = cb_children.getSelectionModel().getSelectedItem();
        selectedDate = checkInDate.getValue();

        // Delegate to FacadeService for restaurant search
        BookNowFacadeService.searchRestaurants(selectedCity, selectedCuisineType, selectedAdults, selectedChildren, selectedDate, restaurantListVBox, this);
    }

    public void handleShowAvailability(Restaurant restaurant) {
        showAvailabilityView(); // Manage UI visibility
        BookNowFacadeService.prepareAvailabilityView(
                restaurant, selectedDate, totalGuests, availabilityVBox,
                table -> handleReserveTable(restaurant, table),
                selectedTimeSlot -> setSelectedTimeSlot(selectedTimeSlot)
        );
    }

    public void handleReserveTable(Restaurant restaurant, Table table) {
        int result = BookNowFacadeService.handleTableReservation(UIUtil.USER, restaurant, table, selectedDate, selectedTimeSlot);

        String title, message;
        if (result == -1) {
            title = "Reservation Failed";
            message = "Reservation already exists for the selected time slot.";
        } else if (result > 0) {
            title = "Reservation Confirmed";
            message = "Your reservation is confirmed.";
        } else {
            title = "Reservation Failed";
            message = "An error occurred while trying to reserve the table.";
        }
        UIUtil.displayAlert(title, message);
    }

    public void handleReadReviews(Restaurant restaurant) {
        reviewsOverlay.setVisible(true);
        reviewsOverlay.setManaged(true);
        BookNowFacadeService.showRestaurantReviews(restaurant, reviewsOverlay);
    }

    public void handleViewMenu(Restaurant restaurant) {
        BookNowFacadeService.getRestaurantUIManager().viewMenu(restaurant);
    }


    public void setSelectedTimeSlot(String selectedTimeSlot) {
        this.selectedTimeSlot = selectedTimeSlot;
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

    // Hides other components that are not needed at the moment
    public void showRestaurantListView() {
        // Hide the availability view
        availabilityVBox.setVisible(false);
        // Hide the reviews overlay when searching new restaurants
        reviewsOverlay.setVisible(false);
        // Show the restaurant list VBox that contains the restaurants, UI elements without it will not show
        restaurantListVBox.setVisible(true);
        // ensure that the VBox is laid out correctly within its parent container
        restaurantListVBox.setManaged(true);
    }

    public void onViewMyReservationsClick(ActionEvent event) {
        UIUtil.displayScene(getClass().getResource("/edu/metrostate/booknow/ReservationsView.fxml"), event);
    }

    public void onViewMyReviewsClick(ActionEvent event) {
        UIUtil.displayScene(getClass().getResource("/edu/metrostate/booknow/ReviewView.fxml"), event);
    }
}
