package edu.calpoly.longbleifer.proximity;

public class Trigger {
	public String uuid;
	public double latitude;
	public double longitude;
	public String name;
	Tab[] tabs;
	
	public Trigger (String uuid) {
		this.uuid = uuid;
	}
	
	public Trigger (String uuid, double latitude, double longitude, String name, Tab[] tabs) {
		this.uuid = uuid;
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
		this.tabs = tabs;
	}
	
	public String toString() {
		return String.format("Name: %s UUID: %s Lat: %f Long: %f", name, uuid, latitude, longitude);
	}
}
