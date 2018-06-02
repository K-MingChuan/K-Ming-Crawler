package Main;

import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Model.Student;
import Model.StudentJsonRepository;

public class Script {
	public static void main(String[] argv){
		StudentJsonRepository allStudentsrepository = new StudentJsonRepository("allStudents.json");
		StudentJsonRepository targetStudentsrepository = new StudentJsonRepository("students.json");
		Set<Student> targetStudents = new HashSet<>(targetStudentsrepository.getStudents());
		Set<Student> allStudents = new HashSet<>(allStudentsrepository.getStudents());
		
		allStudents.removeAll(targetStudents);  //different students
		allStudents.addAll(targetStudents);
		
		targetStudentsrepository.addStudents(new ArrayList<>(allStudents));
	}
}
