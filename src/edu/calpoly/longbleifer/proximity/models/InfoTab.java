package edu.calpoly.longbleifer.proximity.models;


public class InfoTab extends Tab {
	public String html;
	
	public InfoTab(String type, String title, String html, Trigger trigger) {
		super(type, title, trigger);
		this.html = html;
	}
}
