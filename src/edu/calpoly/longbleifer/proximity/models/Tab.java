package edu.calpoly.longbleifer.proximity.models;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.*;
import com.activeandroid.query.Select;
import com.activeandroid.util.Log;

@Table(name = "tabs")
public class Tab extends Model {
	@Column(name = "trigger")
	public Trigger trigger;
	
	@Column(name = "title")
	public String title;
	
	@Column(name = "type")
	public String type;
	
	@Column(name = "position")
	public int position;
	
	@Column(name = "metadata")
	public String metadata;
	
	public Tab() {
		super();
	}
	
	public Tab(String type, String title, int position) {
		super();
		this.type = type;
		this.title = title;
		this.position = position;
		this.metadata = "{}";
	}
	
	public Tab(String type, String title, int position, String metadata) {
		super();
		this.type = type;
		this.title = title;
		this.position = position;
		this.metadata = metadata;
	}
	
	public Tab(String type, String title, int position, Trigger trigger) {
		super();
		this.type = type;
		this.title = title;
		this.position = position;
		this.metadata = "{}";
		this.trigger = trigger;
	}
	
	public Tab(String type, String title, int position, String metadata, Trigger trigger) {
		super();
		this.type = type;
		this.title = title;
		this.position = position;
		this.metadata = metadata;
		this.trigger = trigger;
	}
	
	public static List<Tab> all() {
		return new Select().from(Tab.class).execute();
	}
	
	public String toString() {
		return this.title;
	}
	
	public Object metadata(String key){
		try {
			JSONObject json = new JSONObject(metadata);
			return json.get(key);
		} catch (JSONException e) {
			Log.e("Proximity", e.getMessage());
			return null;
		}
		
	}
}
