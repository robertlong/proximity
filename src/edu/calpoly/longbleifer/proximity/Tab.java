package edu.calpoly.longbleifer.proximity;

public abstract class Tab {
	public String type;
	public String value;
	
	public Tab(String type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public String toString() {
		return this.value;
	}
}
