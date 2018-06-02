package Main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Crawler.CourseCrawler;
import Crawler.StudentCrawler;
import Model.CourseJsonRepository;
import Model.Student;
import Model.StudentJsonRepository;
import Model.StudentRepository;

public class Main {

	public static void main(String[] args) {
		//抓學生資料與修課記錄
//		StudentCrawler studentCrawler = new StudentCrawler(new StudentJsonRepository());
//		studentCrawler.crawl();
		
		//抓課程資料
//		CourseCrawler courseCrawler = new CourseCrawler(new CourseJsonRepository());
//		courseCrawler.crawl();
		
		StudentJsonRepository studentJsonRepository1 = new StudentJsonRepository("students_part");
		StudentJsonRepository studentJsonRepository2 = new StudentJsonRepository("students");
		List<Student> students1 = studentJsonRepository1.getStudents();
		List<Student> students2 = studentJsonRepository2.getStudents();
		Map<String, Student> studentMap = new HashMap<String, Student>();
		for (Student student : students2) {
//			System.out.println(student.toString());
			studentMap.put(student.getId(), student);
		}
		List<Student> newStudent = new ArrayList<>();
		for (Student student : students1) {
			Student s2 = studentMap.get(student.getId());
			s2.setDepartmentNo(student.getDepartmentNo());
			s2.setTransfer(student.isTransfer());
			newStudent.add(s2);
		}
		StudentJsonRepository studentJsonRepository3 = new StudentJsonRepository("students_new");
		studentJsonRepository3.addStudents(newStudent);
	}
}
