package Model;

import java.util.List;

public interface StudentRepository {
	void addStudents(List<Student> students);
	void addStudent(Student student);
	void removeStudent(Student student);
	List<Student> getStudents();
}
