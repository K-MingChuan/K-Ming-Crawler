package Main;
import Crawler.CourseCrawler;
import Crawler.StudentCrawler;
import Model.CourseJsonRepository;
import Model.StudentJsonRepository;

public class Main {

	public static void main(String[] args) {
		StudentCrawler studentCrawler = new StudentCrawler(new StudentJsonRepository());
		studentCrawler.crawl();
		CourseCrawler courseCrawler = new CourseCrawler(new CourseJsonRepository());
		courseCrawler.crawl();
	}
}
