package edu.metrostate.booknow.Services;

import edu.metrostate.booknow.DAO.RestaurantDAO;
import edu.metrostate.booknow.Models.Restaurant;

import java.time.LocalDate;
import java.util.List;

public class RestaurantService {
    private final RestaurantDAO restaurantDAO;

    public RestaurantService(RestaurantDAO restaurantDAO) { // Inject RestaurantDAO here
        this.restaurantDAO = restaurantDAO;
    }

    public List<String> fetchCityNames() {
        return restaurantDAO.fetchCityNames();
    }

    public List<String> fetchCuisineTypes() {
        return restaurantDAO.fetchCuisineTypes();
    }

    public List<Restaurant> getAvailableRestaurants(String selectedCity, String selectedCuisineType, int totalGuests, LocalDate selectedDate) {
        return restaurantDAO.getAvailableRestaurants(selectedCity, selectedCuisineType, totalGuests, selectedDate);
    }
}
