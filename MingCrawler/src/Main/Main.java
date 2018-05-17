package Main;
import Crawler.CourseCrawler;
import Crawler.StudentCrawler;
import Model.CourseJsonRepository;
import Model.StudentJsonRepository;

public class Main {

	public static void main(String[] args) {
		//抓學生資料與修課記錄
		StudentCrawler studentCrawler = new StudentCrawler(new StudentJsonRepository());
		studentCrawler.crawl();
		
		//抓課程資料
//		CourseCrawler courseCrawler = new CourseCrawler(new CourseJsonRepository());
//		courseCrawler.crawl();
	}
}
