package Crawler;

import static Model.MingChuanConstants.SEMESTERS;
import static Model.MingChuanConstants.YEARS;

import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import Model.Student;
import Model.StudentRepository;
import Model.TakenClassesRecord;

public class StudentCrawler extends Cralwer {
	private static final String EPortfolioURL = "http://eportfolio.mcu.edu.tw/ePortfolio/Student/Common/e061.asp?";
	private static final String URL = "https://www.mcu.edu.tw/student/new-query/";
	private StudentRepository studentRepository;
	private String sel1 = "sel-6-1.asp?ch=2";
	private String sel3 = "sel-6-3.asp?ch=2";
	private String tempno = "&tempno=";
	private List<String> links = new ArrayList<>();
	private List<String> departmentNumbers = new ArrayList<>();
	private List<String> departmentUrls= new ArrayList<>();
	private List<String> classnos = new ArrayList<>();
	
	
	public StudentCrawler(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}
	
	@Override
	public void crawl() {
		if (!hasStudentId()) {
			createStudentIdAndName();
		}
		
		// 測試取得單一學生修課記錄
<<<<<<< HEAD
//		Student student = studentRepository.getStudent("03360296");
//		Student s = getStudent(student.getId(), student.getName());
//		System.out.println(student.getId() + student.getName());
//		studentRepository.removeStudent(student);
		// studentRepository.addStudent(s);

		List<Student> students = studentRepository.getStudents();
		List<Student> emptyStudents = new ArrayList<>();
		List<Student> finished = Collections.synchronizedList(new ArrayList<>());
		int startIndex = 0;
		for (int i = 0 ; i < students.size() ; i ++)
			if (students.get(i).getTakenClassesRecords().size() == 0)
				emptyStudents.add(students.get(i));
		emptyStudents.parallelStream().forEach(student -> {
			int random = new Random().nextInt(5000);
			try {
				System.out.println("Thread: " + student.getName() + " accessed! Sleep for " + random + " ms.");
				Thread.sleep(random);
				System.out.println("Thread: " + student.getName() + " woke up! Start crawling!");
				student = getStudent(student.getId(), student.getName());
				studentRepository.removeStudent(student);
				studentRepository.addStudent(student);
				System.out.println(student.getId() + " crawling...");
				finished.add(student);
				System.out.println("Thread: " + student.getName() + " finished!" + (emptyStudents.size() - finished.size()) + " students left.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
=======
		Student student = studentRepository.getStudent("03360296");
		Student s = getStudent(student.getId(), student.getName());
		studentRepository.addStudent(s);
		studentRepository.addStudent(s);
		System.out.println(s.getId() + s.getName() + s.getTakenClassesRecords());
		
>>>>>>> 2b036e45658ab3fe306dd7dfc163281d974378a1
		// 這部分是拿全部學生的修課記錄
//		for (Student student : studentRepository.getStudents()) {
//			Student s = getStudent(student.getId(), student.getName());
//			studentRepository.removeStudent(student);
//			studentRepository.addStudent(s);
//		}
		
	}
	
	private void printStudents(List<Student> students) {
		for (Student student : students) {
			System.out.println(student.toString());
		}
		
	}

	private boolean hasStudentId() {
		return studentRepository.hasStudents();
	}
	
	private List<String> getDepartmentNumbers() {
		List<String> departmentNumbers = new ArrayList<>();
		Document doc = getDoc(URL + sel1);
	    
		Elements trs = doc.select("tbody").select("tr");
		for (int i = 5; i < trs.size(); i++) {
			Elements tds = trs.get(i).select("td");
			for (int j = 0; j < tds.size(); j=j+2) {
				Elements link = tds.get(j).select("a[href]"); 
				links.add(link.attr("href"));
				departmentNumbers.add(tds.get(j).text());
			}

		}
		return departmentNumbers;
	}
	
	private void createClassMeetingUrls(String url) {
		Document classDoc = getDoc(url);
		if (!classDoc.select("center").text().equals("資料檔查無此筆資料!!")) {
			Elements trs = classDoc.select("tbody").select("tr");
			for (int i = 1; i < trs.size(); i++) {
				Elements tds = trs.get(i).select("td");
				if (tds.get(2).text().equals("班會")) {
					Elements link = tds.get(1).select("a[href]");
					departmentUrls.add(URL + link.attr("href"));
				}
			}
		}
		
	}
	
	private Document getDoc(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	private void createStudentIdAndName() {
		departmentNumbers = getDepartmentNumbers();
		
		for (String departmentNumber : departmentNumbers) 
			for (int i = 1; i <= 4; i++) 
				for (int j = 1; j <= 15; j++) {
					String classno = departmentNumber + Integer.toString(i*100 + j);
					createClassMeetingUrls(URL + sel3 + tempno + classno);
				}

		
		List<Student> myStudents = Collections.synchronizedList(new ArrayList<Student>());
		departmentUrls.parallelStream()
						.forEach(url -> myStudents.addAll(getStudents(url)));

		studentRepository.addStudents(myStudents);
	}
	
	private void printDepUrl() {
		for (String string : departmentUrls) {
			System.out.println(string);
		}
	}
	
	private List<Student> getStudents(String url) {
		Document classmeetingDoc = getDoc(url);
		String classstr = classmeetingDoc.select("center").select("font").text();
		String[] tokens = classstr.split("：|　");
		String classno = tokens[1];
		classnos.add(classno);
		String subjectno = tokens[5];
		System.out.println(classno);

		Elements trs = classmeetingDoc.select("tbody").select("tr");
		List<Student> studentlist = new ArrayList<>();
		for (int i = 1; i < trs.size(); i++) {
			Elements tds = trs.get(i).select("td");
			for (int j = 0; j < tds.size(); j=j+3) {
				String name = (tds.get(j+1).text().matches(".*[A-Za-z].*"))?tds.get(j+1).text():tds.get(j+1).text().replaceAll(" ", "");
				Student student = new Student(tds.get(j).text(), name);
				studentlist.add(student);
			}
		}
		return studentlist;
	}

	public Student getStudent(String id, String name) {
		Student student = new Student(id, name);
		List<TakenClassesRecord> takenClassesRecords = new ArrayList<>();
		for (int year : YEARS)
			for (int semester : SEMESTERS) {
				String eUrl = EPortfolioURL + "gyr=" + String.valueOf(year) + "&gsem=" + String.valueOf(semester);

				URLConnection connection = connect(eUrl, "std%5Fno=" + id);
				String response = getResponse(connection, "Big5");
				Document doc = Jsoup.parse(response.toString());

				getTakenClassesRecords(doc);

				takenClassesRecords.addAll(getTakenClassesRecords(doc));

			}

		student.setTakenClassesRecords(takenClassesRecords);

		return student;
	}

	private List<TakenClassesRecord> getTakenClassesRecords(Document doc) {
		List<TakenClassesRecord> takenClassesRecords = new ArrayList<>();
		Elements trs = doc.select("table[class=table_style_small]").select("tr");
		for (int i = 1; i < trs.size() - 2; i++) {
			Elements alink = trs.get(i).select("a[href]");

			String[] tokens = alink.attr("href").split("=");
			int year = Integer.parseInt(tokens[1].substring(0, 3));
			int semester = Integer.parseInt(tokens[2].substring(0, 1));
			String courseId = tokens[3].substring(0, 5);
			String courseName = trs.get(i).select("td").get(1).text();
			int score = Integer.parseInt(tokens[4].substring(0));

			TakenClassesRecord takenClassesRecord = new TakenClassesRecord(year, semester, courseId, courseName, score);
			// System.out.println(takenClassesRecord.toString());
			takenClassesRecords.add(takenClassesRecord);
		}
		return takenClassesRecords;
	}
	
}
