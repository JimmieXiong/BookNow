package edu.metrostate.booknow.Services;

import edu.metrostate.booknow.DAO.*;
import edu.metrostate.booknow.Models.Restaurant;
import edu.metrostate.booknow.Models.Review;
import edu.metrostate.booknow.Utils.DBConnection;
import edu.metrostate.booknow.Utils.RestaurantUIManager;
import edu.metrostate.booknow.Utils.UIUtil;

import java.time.LocalDate;
import java.util.List;

public class BookNowServiceManager {

    private final RestaurantService restaurantService;
    private final ReviewService reviewService;
    private final UserService userService;
    private final TableService tableService;
    private final ReservationService reservationService;
    private final RestaurantUIManager restaurantUIManager;

    public BookNowServiceManager(DBConnection dbHandler) {
        this.userService = new UserService(new UserDAO(dbHandler));
        this.restaurantService = new RestaurantService(new RestaurantDAO(dbHandler));
        this.reviewService = new ReviewService(new ReviewDAO(dbHandler));
        this.tableService = new TableService(new TableDAO(dbHandler));
        this.reservationService = new ReservationService(new ReservationDAO(dbHandler));

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

    public boolean reserveTable(String username, int restaurantId, LocalDate date, String timeSlot, String tableNumber) {
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
}
