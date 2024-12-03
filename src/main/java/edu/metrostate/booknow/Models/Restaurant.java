package edu.metrostate.booknow.Models;

public class Restaurant {

    private int restaurantId;
    private String name;
    private String city;
    private String cuisineType;
    private String description;
    private String menuPdf;
    private String imagePath;
    private int maxGuests;

    public Restaurant(int restaurantId, String name, String city, String cuisineType, String description, String menuPdf, String imagePath, int maxGuests) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.city = city;
        this.cuisineType = cuisineType;
        this.description = description;
        this.menuPdf = menuPdf;
        this.imagePath = imagePath;
        this.maxGuests = maxGuests;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public String getDescription() {
        return description;
    }

    public String getMenuPdf() {
        return menuPdf;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMenuPdf(String menuPdf) {
        this.menuPdf = menuPdf;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }
}
