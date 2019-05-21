package org.dreilaenderhack.model;

public class Document {
	
	private String name;
	private String date;
	
	public Document() {
		super();
	}
	
	public Document(String name, String date) {
		super();
		this.name = name;
		this.date = date;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	

}
