package edu.calpoly.longbleifer.proximity.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.*;

@Table(name = "tabs")
public abstract class Tab extends Model {
	@Column(name = "trigger")
	public Trigger trigger;
	
	@Column(name = "title")
	public String title;
	
	@Column(name = "type")
	public String type;
	
	@Column(name = "position")
	public String position;
	
	@Column(name = "metadata")
	public String metadata;
	
	public Tab() {
		super();
	}
	
	public Tab(String type, String title) {
		super();
		this.type = type;
		this.title = title;
	}
	
	public Tab(String type, String title, Trigger trigger) {
		super();
		this.type = type;
		this.title = title;
		this.trigger = trigger;
	}
	
	public String toString() {
		return this.title;
	}
}
