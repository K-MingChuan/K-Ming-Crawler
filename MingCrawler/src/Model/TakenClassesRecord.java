package Model;

public class TakenClassesRecord {
	private int year;
	private int semester;
	private String courseId;
	private String courseName;
	private int score;

	public TakenClassesRecord(int year, int semester, String courseId, String courseName, int score) {
		super();
		this.year = year;
		this.semester = semester;
		this.courseId = courseId;
		this.courseName = courseName;
		this.score = score;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("yr=%d, sem=%d, cour=%s %s, scor=%d", year, semester, courseId, courseName, score);
	}
}
