package edu.calpoly.longbleifer.proximity.models;

public class RestaurantItem {
	public String name;
	public String description;
	public double price;
	String picture;
	
	public RestaurantItem (String name, double d) {
		this.name = name;
		this.price = d;
	}
	
	public RestaurantItem (String name, double price, String picture) {
		this.name = name;
		this.price = price;
		this.picture = picture;
	}
	
	public RestaurantItem (String name, String description, double price) {
		this.name = name;
		this.description = description;
		this.price = price;
	}
	
	public RestaurantItem (String name, String description, double price, String picture) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.picture = picture;
	}
}
