package Crawler;

import java.net.URLConnection;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import Main.Main;
import Model.Course;
import Model.CourseRepository;

public class CourseCrawler extends Cralwer {
	private static final String CourseURL = "https://tch.mcu.edu.tw/sylwebqry/pro_qry.aspx?";
	private List<String> departmentNumbers = new ArrayList<>();
	private List<Course> courses = new ArrayList<>();
	private CourseRepository courseRepository;

	public CourseCrawler(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}
	
	@Override
	public void crawl() {
		try {
			for(int year: Main.years)
				for (int semester: Main.semesters) {
					if (!hasCourses(year, semester)) {
						createDepartments(year, semester);
						for (String departmentNumber : departmentNumbers)
							createCourseInfo(departmentNumber);
						System.out.println("create course information successfully");
						for (Course course : courses) 
							setUpCourseInfo(course);
						courseRepository.addCourses(year, semester, courses);
						System.out.println("Set Up Course Information successfully");
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean hasCourses(int year, int semester) {
		if (courseRepository.getCourses(year, semester) != null)
			return true;
		return false;
	}
	
	private void createDepartments(int year, int semester) throws Exception {
		String cUrl = CourseURL + "g_year=" + String.valueOf(year) + "&g_sem=" + String.valueOf(semester);
		URLConnection conn = connect(cUrl, Main.sessionIdCookie);
		String response = getResponse(conn, "UTF-8");
		Document doc = Jsoup.parse(response.toString());
		Elements trs = doc.select("#ContentPlaceHolder1_panDept").select("tbody").select("tr");
		for (int i = 1; i < trs.size(); i++) {
			Elements tds = trs.get(i).select("td");
			for (int j = 0; j < tds.size(); j=j+2) 
				departmentNumbers.add(tds.get(j).text());
		}
	}
	
	private void createCourseInfo(String department) throws Exception {
		String departmentUrl = "https://tch.mcu.edu.tw/sylwebqry/pro10_21.aspx?tdept=" + department;
		URLConnection conn = connect(departmentUrl, Main.sessionIdCookie);
		String response = getResponse(conn, "UTF-8");
		Document doc = Jsoup.parse(response.toString());
		System.out.println(doc.select("span[id=ContentPlaceHolder1_labYearSemTitle]span[class=Label]").text() + " " + department);
		Elements trs = doc.select("#ContentPlaceHolder1_panShowNew").select("table").select("tr");
		for (int i = 1; i < trs.size(); i++) {
			Elements alink = trs.get(i).select("a[href]");
			String[] tokens = alink.attr("href").split("=");
			String courseId = tokens[2].substring(0, 5);
			String classId = tokens[1].substring(0, 5);
			int year = Integer.parseInt(tokens[3].substring(0, 3));
			int semester = Integer.parseInt(tokens[4].substring(0, 1));
			Course course = new Course(courseId, classId, year, semester);
			courses.add(course);
		}
	}
	
	private void setUpCourseInfo(Course course) throws Exception {
		String classUrl = String.format("https://tch.mcu.edu.tw/sylwebqry/pro10_22.aspx?tcls=%s&tcour=%s&tyear=%s&tsem=%s&type=1", 
				course.getClassId(), course.getCourseId(), course.getYear(), course.getSemseter());
		URLConnection conn = connect(classUrl, Main.sessionIdCookie);
		String response = getResponse(conn, "UTF-8");
		Document doc = Jsoup.parse(response.toString());
		String name = doc.select("span[id=labCourNm_C]span[class=Label]").text();
		String classGoal = doc.select("span[id=labObj_C]span[class=TdLabel_C]").text();
		String outline = doc.select("span[id=labOle_C]span[class=TdLabel_C]").text();
		String effect = doc.select("span[id=labOutc_C]span[class=TdLabel_C]").text();
		String departmentGoal = doc.select("span[id=labDeptObj_C]span[class=TdLabel_C]").text();
		String reference = doc.select("span[id=labRef_C]span[class=TdLabel_C]").text();
		course.setInformation(name, classGoal, outline, effect, departmentGoal, reference);
	}
	
}
