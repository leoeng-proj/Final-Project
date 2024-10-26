package structs;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import model.Classroom;
import model.Course;

public class ClassroomContainer implements Serializable, Removal{

	private LinkedList<Classroom> classrooms;
	private static final Classroom[] rooms = {
		//Classroom(String roomID, boolean hasProjector, int maxCapacity) {
		new Classroom("CL01", 40),
		new Classroom("CL02", 25),
		new Classroom("CL03", 30),
		new Classroom("CL04", 30),
		new Classroom("CL05", 30),
		new Classroom("CL06", 30),
		new Classroom("CL07", 20),
	};
	
	public ClassroomContainer() {
		super();
		classrooms = new LinkedList<>();
		for(Classroom c : rooms) {
			classrooms.add(c);
		}
	}
	public boolean addCourse(Classroom course) {
		if(classrooms.contains(course)) {
			return false;
		}
		classrooms.add(course);
		return true;
	}
	public Classroom[] toArray() {
		return classrooms.toArray(new Classroom[0]);
	}
	public boolean remove(Object obj) {	
		return classrooms.remove(obj);
	}
	public Classroom getRandomCourse() {
		return classrooms.get((int)(Math.random()*classrooms.size()));
	}
}
