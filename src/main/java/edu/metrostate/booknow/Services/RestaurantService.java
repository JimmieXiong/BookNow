package edu.metrostate.booknow.Services;

import edu.metrostate.booknow.DAO.RestaurantDAO;
import edu.metrostate.booknow.Models.Restaurant;

import java.time.LocalDate;
import java.util.List;

/**
 * Provides services for restaurant-related operations, including fetching city names and cuisine types,
 * validating search criteria, and finding available restaurants.
 */
public class RestaurantService {

    private final RestaurantDAO restaurantDAO;
    private String validationMessage;

    private static final String locationValidationError = "Please select a location.";
    private static final String cuisineValidationError = "Please select a cuisine type.";
    private static final String dateValidationError = "Please select a date.";
    private static final String pastDateValidationError = "Check-in date cannot be in the past.";

    public RestaurantService() {
        this.restaurantDAO = new RestaurantDAO();
    }

    public List<String> fetchCityNames() {
        return restaurantDAO.fetchCityNames();
    }

    public List<String> fetchCuisineTypes() {
        return restaurantDAO.fetchCuisineTypes();
    }

    public boolean validateSearchCriteria(String city, String cuisineType, LocalDate date) {
        return validateCity(city) && validateCuisineType(cuisineType) && validateDate(date);
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public List<Restaurant> findAvailableRestaurants(String city, String cuisineType, LocalDate date) {
        return restaurantDAO.getAvailableRestaurants(city, cuisineType, date);
    }

    public String fetchAverageRating(int restaurantId) {
        return restaurantDAO.getAverageRating(restaurantId);
    }

    private boolean validateCity(String city) {
        if (city == null) {
            validationMessage = locationValidationError;
            return false;
        }
        return true;
    }

    private boolean validateCuisineType(String cuisineType) {
        if (cuisineType == null) {
            validationMessage = cuisineValidationError;
            return false;
        }
        return true;
    }

    private boolean validateDate(LocalDate date) {
        if (date == null) {
            validationMessage = dateValidationError;
            return false;
        }
        if (date.isBefore(LocalDate.now())) {
            validationMessage = pastDateValidationError;
            return false;
        }
        return true;
    }
}