package com.oncourse;


public class CoursePackage {
	private String name;
	private String courseCode;
	private int cpid;

	public CoursePackage(String name, String courseCode, int cpid){
		this.name = name;
		this.courseCode = courseCode;
		this.cpid = cpid;
	}

	public String getName() {
		return name;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public int getCpid() {
		return cpid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public void setName(int cpid) {
		this.cpid = cpid;
	}

}
