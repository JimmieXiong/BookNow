package edu.metrostate.booknow.Services;

import edu.metrostate.booknow.Controllers.BookNowController;
import edu.metrostate.booknow.DAO.*;
import edu.metrostate.booknow.Models.Restaurant;
import edu.metrostate.booknow.Models.Review;
import edu.metrostate.booknow.Models.Table;
import edu.metrostate.booknow.Utils.DBConnection;
import edu.metrostate.booknow.Utils.RestaurantUIManager;
import edu.metrostate.booknow.Utils.UIUtil;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;


/*
 This class acts as a facade, encapsulating interactions with multiple services (RestaurantService,
 ReviewService, TableService, etc.) and providing simplified methods for higher-level operations like
 searching restaurants and showing availability.
 */

public class BookNowFacadeService {

    private final RestaurantService restaurantService;
    private final ReviewService reviewService;
    private final AuthenticationService userService;
    private final TableService tableService;
    private final ReservationService reservationService;
    private final RestaurantUIManager restaurantUIManager;

    public BookNowFacadeService(DBConnection DBConnection) {
        this.userService = new AuthenticationService(new UserDAO(DBConnection));
        this.restaurantService = new RestaurantService(new RestaurantDAO(DBConnection));
        this.reviewService = new ReviewService(new ReviewDAO(DBConnection));
        this.tableService = new TableService(new TableDAO(DBConnection));
        this.reservationService = new ReservationService(new ReservationDAO(DBConnection));

        this.restaurantUIManager = new RestaurantUIManager(reviewService, tableService);
    }

    public List<String> getCityNames() {
        return restaurantService.fetchCityNames();
    }

    public List<String> getCuisineTypes() {
        return restaurantService.fetchCuisineTypes();
    }

    public List<Restaurant> getAvailableRestaurants(String city, String cuisineType, int totalGuests, LocalDate date) {
        return restaurantService.getAvailableRestaurants(city, cuisineType, totalGuests, date);
    }

    public List<Review> getReviewsByRestaurantId(int restaurantId) {
        return reviewService.getReviewsByRestaurantId(restaurantId);
    }

    public int reserveTable(String username, int restaurantId, LocalDate date, String timeSlot, String tableNumber) {
        return tableService.reserveTable(username, restaurantId, date, timeSlot, tableNumber);
    }


    public boolean validateSearchInputs(String city, String cuisineType, Integer adults, Integer children, LocalDate date) {
        if (city == null) {
            UIUtil.displayAlert("Location Selection", "Please select a location.");
            return false;
        }
        if (cuisineType == null) {
            UIUtil.displayAlert("Cuisine Type Selection", "Please select a cuisine type.");
            return false;
        }
        if (adults == null || adults <= 0) {
            UIUtil.displayAlert("Adults Selection", "Please select the number of adults (must be at least 1).");
            return false;
        }
        if (children == null) {
            UIUtil.displayAlert("Children Selection", "Please select the number of children (can be 0 if none).");
            return false;
        }
        if (date == null || date.isBefore(LocalDate.now())) {
            UIUtil.displayAlert("Date Selection", "Please select a valid date.");
            return false;
        }
        return true;
    }

    public RestaurantUIManager getRestaurantUIManager() {
        return restaurantUIManager;
    }

    public void searchRestaurants(String city, String cuisineType, Integer adults, Integer children, LocalDate date, VBox restaurantListVBox, BookNowController controller) {
        if (validateSearchInputs(city, cuisineType, adults, children, date)) {
            int totalGuests = adults + children;

            List<Restaurant> restaurants = getAvailableRestaurants(city, cuisineType, totalGuests, date);
            restaurantUIManager.populateRestaurantListVBox(
                    restaurantListVBox, restaurants, controller::handleReadReviews,
                    controller::handleViewMenu, controller::handleShowAvailability
            );
        }
    }

    public void showAvailability(Restaurant restaurant, LocalDate selectedDate, int totalGuests, VBox availabilityVBox, VBox restaurantListVBox, VBox reviewsOverlay, BookNowController controller) {
        // Delegate UI-specific actions to the controller
        controller.showAvailabilityView();
        availabilityVBox.getChildren().clear();

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> controller.showRestaurantListView());
        availabilityVBox.getChildren().add(backButton);

        Label availabilityLabel = new Label("Available Tables for " + restaurant.getName());
        availabilityLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #003580; -fx-font-weight: bold;");
        availabilityVBox.getChildren().add(availabilityLabel);

        TableView<Table> tableView = restaurantUIManager.createTableView(restaurant, selectedDate, totalGuests, table -> controller.handleReserveTable(restaurant, table), selectedTimeSlot -> controller.setSelectedTimeSlot(selectedTimeSlot));

        availabilityVBox.getChildren().add(tableView);
        reviewsOverlay.setVisible(false);
        reviewsOverlay.setManaged(false);
    }
}
