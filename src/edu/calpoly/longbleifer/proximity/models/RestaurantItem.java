package edu.calpoly.longbleifer.proximity.models;

public class RestaurantItem {
	public String name;
	public String description;
	public double price;
	public String image;
	
	public RestaurantItem (String name, double price) {
		this.name = name;
		this.price = price;
	}
	
	public RestaurantItem (String name, double price, String image) {
		this.name = name;
		this.price = price;
		this.image = image;
	}
	
	public RestaurantItem (String name, String description, double price) {
		this.name = name;
		this.description = description;
		this.price = price;
	}
	
	public RestaurantItem (String name, String description, double price, String image) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.image = image;
	}
}
