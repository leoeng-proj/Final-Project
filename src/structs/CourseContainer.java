package structs;

import java.io.Serializable;
import java.util.LinkedList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Course;

public class CourseContainer implements Serializable, Removal{

	private LinkedList<Course> courses;
	
	public CourseContainer() {
		super();
		courses = new LinkedList<>();
	}
	
	public void addCourse(Course course) {
		if(courses.contains(course)) {
			return;
		}
		courses.add(course);
	}
	public ObservableList<Course> getObservableCourseContainer(){
		return FXCollections.observableArrayList(toArray());
	}
	public Course getRandomCourse() {
		return courses.get((int)(Math.random()*courses.size()));
	}
	public boolean isEmpty() {
		return courses.isEmpty();
	}
	public boolean remove(Object obj) {
		return courses.remove(obj);
	}
	public Course[] toArray() {
		return courses.toArray(new Course[0]);
	}
}
