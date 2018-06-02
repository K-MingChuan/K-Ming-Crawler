package Model;

import java.util.ArrayList;
import java.util.List;

public class Student {
	private String id;
	private String name;
	private String departmentNo;
	private boolean gender;
	private boolean transfer;
	private List<TakenClassesRecord> takenClassesRecords = new ArrayList<>();
	
	public Student(String id) {
		this.id = id;
	}
	
	public Student(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Student(String id, String name, String departmentNo) {
		this.id = id;
		this.name = name;
		this.departmentNo = departmentNo;
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
		return String.format("id = %s, name = %s, departmentNo = %s\n%s", id, name, departmentNo, classesRecord);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getDepartmentNo() {
		return departmentNo;
	}

	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}

	public boolean isTransfer() {
		return transfer;
	}

	public void setTransfer(boolean transfer) {
		this.transfer = transfer;
	}

}
