package com.melihhan.projects;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FoodQuick {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
        	// Captures details for customer, restaurant and orders including items & special instructions.
            Customer customer = captureCustomerDetails();
            AbstractMap.SimpleEntry<List<Order>, String> orderDetails = captureOrderDetails();
            List<Order> orderItems = orderDetails.getKey();
            String instructions = orderDetails.getValue();
            Restaurant restaurant = captureRestaurantDetails();
            // Loading available drivers from a file
            List<Driver> drivers = loadDriversFromFile();
            // Generating and saving the invoice
            generateInvoice(customer, orderItems, restaurant, drivers, instructions);
        } finally {
            scanner.close();
        }
    }

    // Method to get the customer details
    private static Customer captureCustomerDetails() {
        System.out.println("Enter your order number: ");
        String orderNumber = scanner.nextLine();

        System.out.println("Enter your name: ");
        String customerName = scanner.nextLine();

        System.out.println("Enter your email: ");
        String email = scanner.nextLine();

        System.out.println("Enter your phone number: ");
        String phone = scanner.nextLine();

        System.out.println("Enter your address: ");
        String address = scanner.nextLine();

        System.out.println("Enter your location (city): ");
        String customerLocation = scanner.nextLine();

        return new Customer(orderNumber, customerName, phone, email, customerLocation, address);
    }
   
        // Enum for menu items; simplifies adding or modifying items in the future
        private enum MenuItem {
        PEPPERONI_PIZZA("Pepperoni pizza", 12.00),
        HAWAIIAN_PIZZA("Hawaiian pizza", 14.00);

        private String name;
        private double price;

        MenuItem(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
    }
    // Method to capture ordered items and special instructions
    private static AbstractMap.SimpleEntry<List<Order>, String> captureOrderDetails() {
        List<Order> orderItems = new ArrayList<>();
        String continueOrdering = "yes";

        while (continueOrdering.equalsIgnoreCase("yes")) {
            MenuItem selectedMenuItem = getOrderChoice();
            int quantity = getQuantity(selectedMenuItem.getName());

            orderItems.add(new Order(selectedMenuItem.getName(), quantity, selectedMenuItem.getPrice()));
            continueOrdering = askUser("Add anything else to your order? (yes/no)");
        }

        System.out.println("Any special instructions for your order?");
        String instructions = scanner.nextLine();
        return new AbstractMap.SimpleEntry<>(orderItems, instructions);
    }

    // Helper method to get the user's choice of food item from the menu
    private static MenuItem getOrderChoice() {
        // Loops until a valid menu item is chosen
        while (true) {
            System.out.println("What would you like to order?");
            int count = 1;
            for (MenuItem item : MenuItem.values()) {
                System.out.printf("%d. %s (£%.2f) \n", count, item.getName(), item.getPrice());
                count++;
            }
            try {
                int orderChoice = Integer.parseInt(scanner.nextLine());
                if (orderChoice >= 1 && orderChoice <= MenuItem.values().length) {
                    return MenuItem.values()[orderChoice - 1];
                }
                System.out.println("This is not on the menu, try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, try again.");
            }
        }
    }

    // Helper method to get quantity for the selected item
    private static int getQuantity(String orderName) {
        while (true) {
            System.out.println("How many quantities of " + orderName + " would you like to order?");
            try {
                int quantity = Integer.parseInt(scanner.nextLine());
                if (quantity > 0) {
                    return quantity;
                }
                System.out.println("Please enter a valid quantity.");
            } catch (NumberFormatException e) {
                System.out.println("Please only enter a number.");
            }
        }
    }
    // Helper method to ask the user a yes/no question and get the response
    private static String askUser(String message) {
        String response;
        while (true) {
            System.out.println(message);
            response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("yes") || response.equals("no")) {
                return response;
            } else {
                System.out.println("Please input 'yes' or 'no'.");
            }
        }
    }
    // Method to capture restaurant details
    private static Restaurant captureRestaurantDetails() {
        System.out.println("Enter restaurant name: ");
        String restaurantName = scanner.nextLine();

        System.out.println("Enter restaurant location: ");
        String restaurantLocation = scanner.nextLine();

        System.out.println("Enter restaurant contact number: ");
        String restaurantContactNumber = scanner.nextLine();

        return new Restaurant(restaurantName, restaurantLocation, restaurantContactNumber);
    }
    // Method to load driver details from a file
    private static List<Driver> loadDriversFromFile() {
        List<Driver> drivers = new ArrayList<>();
        try (Scanner driverScanner = new Scanner(new File("drivers.txt"))) {
            while (driverScanner.hasNextLine()) {
                String[] details = driverScanner.nextLine().split(", ");
                String driverName = details[0];
                String driverLocation = details[1];
                int driverDeliveryLoad = 0;
                if (details.length >= 3 && !details[2].isEmpty()) {
                    driverDeliveryLoad = Integer.parseInt(details[2]);
                }
                drivers.add(new Driver(driverName, driverLocation, driverDeliveryLoad));
            }
        } catch (IOException e) {
            System.out.println("Error reading drivers.txt file.");
            e.printStackTrace();
        }
        return drivers;
    }
    // Method to generate and save the invoice
    private static void generateInvoice(Customer customer, List<Order> orderItems, Restaurant restaurant, List<Driver> drivers, String instructions) {
        // Uses try-with-resources to automatically close the BufferedWriter
    	try (BufferedWriter writer = new BufferedWriter(new FileWriter("invoice.txt", false))) {
            writer.write("Order number: " + customer.getOrderNumber() + "\n");
            writer.write("Customer: " + customer.getCustomerName() + "\n");
            writer.write("Email: " + customer.getEmail() + "\n");
            writer.write("Phone number: " + customer.getPhone() + "\n");
            writer.write("Location: " + customer.getCustomerLocation() + "\n");
            writer.newLine();
            writer.write("You have ordered the following from " + restaurant.getRestaurantName() + " in " + restaurant.getRestaurantLocation() + ":");
            writer.newLine();
            writer.newLine();

            double total = 0.0;
            for (Order order : orderItems) {
                writer.write(order.getQuantity() + " x " + order.getItemName() + " (£" + String.format("%.2f", order.getPrice()) + " each)\n");
                total += order.getQuantity() * order.getPrice();
            }
            writer.newLine();
            writer.write("Special instructions: " + instructions + "\n");
            writer.newLine();
            writer.write("Total: £" + String.format("%.2f", total) + "\n");
            writer.newLine();

            Driver selectedDriver = null;
            int minLoad = Integer.MAX_VALUE;

            for (Driver driver : drivers) {
                if (driver.getDriverLocation().equalsIgnoreCase(customer.getCustomerLocation()) && driver.getDriverLocation().equalsIgnoreCase(restaurant.getRestaurantLocation()) && driver.getDeliveryLoad() < minLoad) {
                    selectedDriver = driver;
                    minLoad = driver.getDeliveryLoad();
                }
            }

            if (selectedDriver != null) {
                writer.write(selectedDriver.getDriverName() + " is nearest to the restaurant and so they will be delivering your order to you at: ");
                writer.newLine();
                writer.newLine();
                writer.write(customer.getAddress());
                writer.newLine();
                writer.newLine();
                writer.write("If you need to contact the restaurant, their number is " + restaurant.getContactNumber() + ".\n");
            } else {
                writer.write("Sorry! Our drivers are too far away from you to be able to deliver to your location.\n");
            }

            System.out.println("Invoice has been created and saved as invoice.txt.");
        } catch (IOException e) {
            // Catches any IO exceptions that might occur during file operations
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}






