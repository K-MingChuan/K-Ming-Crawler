package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class StudentJsonRepository implements StudentRepository {

	private Set<Student> students = Collections.synchronizedSet(new HashSet<Student>()); 
	private String filename = "students";

	public StudentJsonRepository(){
		readFile();
	}
	
	public StudentJsonRepository(String filename) {
		this.filename = filename;
		readFile();
	}
	
	@Override
	public void addStudents(List<Student> students) {
		this.students.removeAll(students);
		this.students.addAll(students);
		writeFile();
	}

	@Override
	public void addStudent(Student student) {
		students.add(student);
		writeFile();
	}

	@Override
	public void removeStudent(Student student) {
		students.remove(student);
		writeFile();
	}

	@Override
	public List<Student> getStudents() {
		return new ArrayList<Student>(students);
	}

	@Override
	public Student getStudent(String id) {
		for (Student student: students)
			if (student.getId().equals(id))
				return student;
		return null;
	}
	
	@Override
	public boolean hasStudents() {
		return !students.isEmpty();
	}

	private void readFile() {
		Type type = new TypeToken<List<Student>>() {}.getType();
		Gson gson = new Gson();
		JsonReader reader;
		if (new File(filename + ".json").exists()) {
			try {
				reader = new JsonReader(new BufferedReader(new InputStreamReader(new FileInputStream(filename + ".json"),"UTF-8")));
				students = Collections.synchronizedSet(new HashSet<Student>(gson.fromJson(reader, type)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private synchronized void writeFile() {
		if (students != null) {
			Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename + ".json"),"UTF-8"))) {
				gson.toJson(new ArrayList(students), writer);
				System.out.println("ºg¿…ßπ¶®");
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
