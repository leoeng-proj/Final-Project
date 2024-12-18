package structs;

import java.io.Serializable;
import java.util.PriorityQueue;

import control.DataCenter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Course;
import model.Professor;
import model.Section;

public class MasterContainer implements Serializable{
	private CourseContainer courses;
	private StudentContainer students;
	private SectionContainer sections;
	private ClassroomContainer classrooms;
	private ProfessorContainer professors;
	public MasterContainer() {
		courses = new CourseContainer();
		students = new StudentContainer();
		classrooms = new ClassroomContainer();
		sections = new SectionContainer(classrooms.size()*12*7); //each classroom can hold a MAXIMUM of 12 sections a day 7 days a week
		professors = new ProfessorContainer();
	}
	public ClassroomContainer getClassroomContainer() {
		return classrooms;
	}
	public CourseContainer getCourseContainer() {
		return courses;
	}
	public ProfessorContainer getProfessorContainer() {
		return professors;
	}
	public SectionContainer getSectionContainer() {
		return sections;
	}
	public StudentContainer getStudentContainer() {
		return students;
	}
	public void autoAssign() {
		if(professors.isEmpty()) {
			Alert noCourses = new Alert(AlertType.WARNING);
			noCourses.setHeaderText("No Professors To Assign");
			noCourses.show();
			return;
		}
		PriorityQueue<Professor> q = new PriorityQueue<Professor>(professors.size());
		q.addAll(professors.getProfessors().values());
		StringBuilder sb = new StringBuilder();
		while(!q.isEmpty()) {
			Professor p = q.poll();
			SectionContainer potentialSections = getPotentialSections(p);
			while(p.getCredits() < Professor.MIN_CREDITS) {
				Section best = getBestSection(p, potentialSections);
				if(best == null) {
					sb.append("Not Enough " + p.getMajor()  + " " + p.getPrefTime() + " Sections for " + p + "\n");
					break;
				}
				assign(p, best);
			}
		}
		if(!sb.isEmpty()) {
			Alert noCourses = new Alert(AlertType.WARNING);
			noCourses.setHeaderText("Check Sections");
			noCourses.setContentText(sb.toString());
			noCourses.show();
		}
	}
	private Section getBestSection(Professor p, SectionContainer section) {
		return section.getBestSection(p);
	}
	private SectionContainer getPotentialSections(Professor p) {
		SectionContainer sects = new SectionContainer(sections);
		sects.trim(s -> {
			return !s.getCourse().getMajor().equals(p.getMajor())  ||
					s.getInstructor() != null;
		});
		return sects;
	}
	public void assign(Professor prof, Section sec) {
		if(prof == null) {
			return;
		}
		prof.addSection(sec);
		if(sec.getInstructor() != null) {
			sec.getInstructor().removeSection(sec);
		}
		sec.setInstructor(prof);
	}
}
