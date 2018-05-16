package Model;

import java.util.ArrayList;
import java.util.List;

public class Student {
	private String id;
	private String name;
	private boolean gender;
	private List<TakenClassesRecord> takenClassesRecords = new ArrayList<>();
	
	public Student(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Student(String id, String name, List<TakenClassesRecord> takenClassesRecords) {
		super();
		this.id = id;
		this.name = name;
		this.takenClassesRecords = takenClassesRecords;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public List<TakenClassesRecord> getTakenClassesRecords() {
		return takenClassesRecords;
	}

	public void setTakenClassesRecords(List<TakenClassesRecord> takenClassesRecords) {
		this.takenClassesRecords = takenClassesRecords;
	}

	@Override
	public String toString() {
		String classesRecord = "";
		for (TakenClassesRecord takenClassesRecord : takenClassesRecords)
			classesRecord += takenClassesRecord.toString() + "\n";
		return String.format("id = %s, name = %s\n%s", id, name, classesRecord);
	}
}
