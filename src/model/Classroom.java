package model;

import java.io.Serializable;

import structs.SectionContainer;

public class Classroom implements Serializable{
	
	private String roomID;
	private int maxCapacity;
	private SectionContainer sections;
	
	public Classroom(String roomID, int maxCapacity) {
		super();
		this.roomID = roomID;
		this.maxCapacity = maxCapacity;
		this.sections = new SectionContainer();
	}
	public String getRoomID() {
		return roomID;
	}
	public int getMaxCapacity() {
		return maxCapacity;
	}
	public SectionContainer getSections() {
		return sections;
	}
}