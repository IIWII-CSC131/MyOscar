package com.iiwii.endpoints;

import java.util.ArrayList;


public class Endpoints {
	ArrayList<String> names;
	private int year;
	
	public Endpoints(ArrayList<String> names) {
		this.names = names;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public ArrayList<String> getNames() {
		return names;
	}
	public void setNames(ArrayList<String> names) {
		this.names = names;
	}
}