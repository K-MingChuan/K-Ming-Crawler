package Model;

public class Course {
	private String courseId;
	private String classId;
	private int year;
	private int semester;
	private String name;
	private String classGoal;
	private String outline;
	private String effect;
	private String departmentGoal;
	private String reference;

	public Course(String courseId, String classId, int year, int semseter) {
		super();
		this.courseId = courseId;
		this.classId = classId;
		this.year = year;
		this.semester = semseter;
	}

	public void setInformation(String name, String classGoal, String outline, String effect, String departmentGoal,
			String reference) {
		this.name = name;
		this.classGoal = classGoal;
		this.outline = outline;
		this.effect = effect;
		this.departmentGoal = departmentGoal;
		this.reference = reference;
	}
	
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getSemseter() {
		return semester;
	}

	public void setSemseter(int semseter) {
		this.semester = semseter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassGoal() {
		return classGoal;
	}

	public void setClassGoal(String classGoal) {
		this.classGoal = classGoal;
	}

	public String getOutline() {
		return outline;
	}

	public void setOutline(String outline) {
		this.outline = outline;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public String getDepartmentGoal() {
		return departmentGoal;
	}

	public void setDepartmentGoal(String departmentGoal) {
		this.departmentGoal = departmentGoal;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s, %s, %s, %s, %s", courseId, classId, name, year, semester);
	}
	
}
