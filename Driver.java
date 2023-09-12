package com.melihhan.projects;

public class Driver {
    // Fields for the Driver class
    private String driverName;
    private String driverLocation;
    private int deliveryLoad;  

    // Constructor
    public Driver(String driverName, String driverLocation, int deliveryLoad) {
        this.driverName = driverName;
        this.driverLocation = driverLocation;
        // deliveryLoad can represent the number of deliveries a driver is currently handling
        this.deliveryLoad = deliveryLoad;  
    }
    // Getters
    public String getDriverName() {
        return driverName;
    }

    public String getDriverLocation() {
        return driverLocation;
    }

    public int getDeliveryLoad() {  
        return deliveryLoad;  
    }
}


