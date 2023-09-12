package com.melihhan.projects;

//Order class represents an order with name, quantity, and price
public class Order {
  
	private String itemName; 
	private int quantity;
	private double price;

  public Order(String itemName, int quantity, double price) {

	  this.itemName = itemName;
      this.quantity = quantity;
      this.price = price;
  }

  public String getItemName() {
      return itemName;
  }

  public int getQuantity() {
      return quantity;
  }

  public double getPrice() {
      return price;
  }
}

