package com.melihhan.projects;

public class Customer {
    // Fields (or member variables) for the Customer class
    private String orderNumber;
    private String customerName;
    private String phone;
    private String email;
    private String customerLocation;
    private String address;  

    // Constructor - this is called when a new Customer object is created
    public Customer(String orderNumber, String customerName, String phone, String email, String customerLocation, String address) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.phone = phone;
        this.email = email;
        this.customerLocation = customerLocation;
        this.address = address; 
    }

    // Getters (or accessor methods) - these allow access to the private fields of the class
    public String getOrderNumber() { return orderNumber; }
    public String getCustomerName() { return customerName; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getCustomerLocation() { return customerLocation; }
    public String getAddress() { return address; }  
}
