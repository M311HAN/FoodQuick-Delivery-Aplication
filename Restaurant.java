package com.melihhan.projects;

public class Restaurant {
    // Fields for the Restaurant class
    private String restaurantName;
    private String restaurantLocation;
    private String contactNumber;

    // Constructor
    public Restaurant(String restaurantName, String restaurantLocation, String contactNumber) {
        this.restaurantName = restaurantName;
        this.restaurantLocation = restaurantLocation;
        this.contactNumber = contactNumber;
    }


    // Getters
    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantLocation() {
        return restaurantLocation;
    }

    public String getContactNumber() {
        return contactNumber;
    }
}
