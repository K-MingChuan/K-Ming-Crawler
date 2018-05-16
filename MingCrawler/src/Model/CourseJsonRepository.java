package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import Main.Main;

public class CourseJsonRepository implements CourseRepository {
	private Map<String, List<Course>> courseMap = new HashMap<>();
	private String filename = "courses";
	
	public CourseJsonRepository() {
		readFile();
	}
	
	@Override
	public void addCourses(int year, int semester, List<Course> courses) {
		courseMap.put(String.valueOf(year*10 + semester), courses);
		writeFile(year, semester);
	}

	@Override
	public void addCourse(int year, int semester, Course course) {
		String key = String.valueOf(year*10 + semester);
		List<Course> courses = courseMap.get(key);
		courses.add(course);
		courseMap.put(key, courses);
		writeFile(year, semester);
	}

	@Override
	public void removeCourse(int year, int semester, Course course) {
		String key = String.valueOf(year*10 + semester);
		List<Course> courses = courseMap.get(key);
		courses.remove(course);
		courseMap.put(key, courses);
		writeFile(year, semester);
	}

	@Override
	public List<Course> getCourses(int year, int semester) {
		String key = String.valueOf(year*10 + semester);
		return courseMap.get(key);
	}
	
	private void readFile() {
		List<Course> courses = new ArrayList<>();
		for (int year : Main.years) {
			for (int semester : Main.semesters) {
				Type type = new TypeToken<List<Course>>() {}.getType();
				Gson gson = new Gson();
				JsonReader reader;
				if (new File(String.format("%s_%s_%s.json", filename, String.valueOf(year), String.valueOf(semester))).exists()) {
					try {
						reader = new JsonReader(new FileReader(String.format("%s_%s_%s.json", filename, String.valueOf(year), String.valueOf(semester))));
						courses = gson.fromJson(reader, type);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					String key = String.valueOf(year*10 + semester);
					courseMap.put(key, courses);
				}
			}
		}
		
	}
	
	@Override
	public void writeFile(int year, int semester) {
		String key = String.valueOf(year*10 + semester);
		List<Course> courses = courseMap.get(key);
		if (courses != null) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			try (Writer writer = new FileWriter(String.format("%s_%s_%s.json", filename, String.valueOf(year), String.valueOf(semester)))) {
				gson.toJson(courses, writer);
				System.out.println("ºg¿…ßπ¶®");
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
