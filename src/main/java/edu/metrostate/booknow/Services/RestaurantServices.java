package edu.metrostate.booknow.Services;

import edu.metrostate.booknow.DAO.RestaurantDAO;
import edu.metrostate.booknow.Models.Restaurant;

import java.time.LocalDate;
import java.util.List;

/**
 * The RestaurantServices class provides various services related to restaurant management,
 * including fetching city names, cuisine types, validating search criteria, and finding available restaurants.
 */

public class RestaurantServices {

    private final RestaurantDAO restaurantDAO;
    private String validationMessage;

    public RestaurantServices() {
        this.restaurantDAO = new RestaurantDAO();
    }

    // Fetch city names from the DAO
    public List<String> getCityNames() {
        return restaurantDAO.fetchCityNames();
    }

    // Fetch cuisine types from the DAO
    public List<String> getCuisineTypes() {
        return restaurantDAO.fetchCuisineTypes();
    }

    // Business logic: Validates the search criteria before querying the DAO
    public boolean isSearchCriteriaValid(String city, String cuisineType, LocalDate date) {
        if (city == null) {
            validationMessage = "Please select a location.";
            return false;
        }
        if (cuisineType == null) {
            validationMessage = "Please select a cuisine type.";
            return false;
        }
        if (date == null) {
            validationMessage = "Please select a date.";
            return false;
        }
        if (date.isBefore(LocalDate.now())) {
            validationMessage = "Check-in date cannot be in the past.";
            return false;
        }
        return true;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    // Finds available restaurants by calling the DAO
    public List<Restaurant> findAvailableRestaurants(String city, String cuisineType, LocalDate date) {
        return restaurantDAO.getAvailableRestaurants(city, cuisineType, date);
    }

    public String getAverageRating(int restaurantId) {
        return restaurantDAO.getAverageRating(restaurantId);
    }
}
