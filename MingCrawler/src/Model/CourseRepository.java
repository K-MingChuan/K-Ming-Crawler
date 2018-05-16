package Model;

import java.util.List;

public interface CourseRepository {
	void addCourses(int year, int semester, List<Course> courses);
	void addCourse(int year, int semester, Course course);
	void removeCourse(int year, int semester, Course course);
	List<Course> getCourses(int year, int semester);
	void writeFile(int year, int semester);
}
