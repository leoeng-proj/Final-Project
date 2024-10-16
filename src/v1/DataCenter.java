package v1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import v1.structs.CourseContainer;
import v1.structs.SectionContainer;
import v1.structs.StudentContainer;

public class DataCenter implements Serializable{
	
	private static final File laptop = new File("C:\\2023-24\\cse148\\workspace\\Projects\\src\\project2\\user.dat");
	private static final File home = new File("N:\\java\\cse218 final project");
	private static final File file = new File("data.dat");

	
	private CourseContainer courses;
	private StudentContainer students;
	private SectionContainer sections;
	
	private static DataCenter instance;
	
	private DataCenter() {
		courses = new CourseContainer();
		students = new StudentContainer();
		sections = new SectionContainer();
	}
	public static DataCenter getInstance() {
		if(instance == null) {
			instance = load();
			if(instance == null) {
				instance = new DataCenter();
			}
		}
		return instance;
	}
	public CourseContainer getCourseContainer() {
		return courses;
	}
	public StudentContainer getStudentContainer() {
		return students;
	}
	public SectionContainer getSectionContainer() {
		return sections;
	}
	public boolean save() {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){
			oos.writeObject(instance);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	private static DataCenter load() {
		try(FileInputStream fis = new FileInputStream(file)){
			ObjectInputStream ois = new ObjectInputStream(fis);
			while(fis.available() > 0) {
				return (DataCenter)ois.readObject();
			}
		}
		catch(FileNotFoundException fnfe) {
			//do nothing, file will be created on close of program
			//could call constructor here
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
