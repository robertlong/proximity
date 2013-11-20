package edu.calpoly.longbleifer.proximity.models;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.*;
import com.activeandroid.query.Select;

@Table(name = "triggers")
public class Trigger extends Model {
	
	@Column(name = "uuid")
	public String uuid;
	
	@Column(name = "latitude")
	public double latitude;
	
	@Column(name = "longitude")
	public double longitude;
	
	@Column(name = "name")
	public String name;
	
	public Trigger() {
		super();
	}
	
	public Trigger (String uuid) {
		super();
		this.uuid = uuid;
	}
	
	public Trigger (String uuid, double latitude, double longitude, String name) {
		this.uuid = uuid;
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
	}
	
	public static List<Trigger> all() {
		return new Select().from(Trigger.class).execute(); 
	}
	
	public static Trigger find(long id) {
		return new Select().from(Trigger.class).where("id = ?", id).executeSingle();
	}
	
	public List<Tab> tabs() {
		return new Select().from(Tab.class).where("tabs.trigger =?", getId()).orderBy("position").execute();
	}
	
	public String toString() {
		return String.format("Name: %s UUID: %s Lat: %f Long: %f", name, uuid, latitude, longitude);
	}
}
