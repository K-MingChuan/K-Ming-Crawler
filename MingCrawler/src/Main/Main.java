package Main;
import java.util.List;

import Crawler.CourseCrawler;
import Crawler.StudentCrawler;
import Model.Course;
import Model.CourseJsonRepository;
import Model.CourseRepository;
import Model.StudentJsonRepository;

public class Main {
	public static final int[] years = { 103, 104, 105, 106 };
	public static final int[] semesters = { 1, 2 };
	public static final String sessionIdCookie = "ASP.NET_SessionId=";	// 填入自己的cookie 

	public static void main(String[] args) {
		StudentCrawler studentCrawler = new StudentCrawler(new StudentJsonRepository());
		studentCrawler.crawl();
		CourseCrawler courseCrawler = new CourseCrawler(new CourseJsonRepository());
		courseCrawler.crawl();
	}
}
