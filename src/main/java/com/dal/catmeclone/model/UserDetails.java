package com.dal.catmeclone.model;


public class UserDetails {

	private String bannerId;
	private int courseId;
	private String courseName;
	private String role_tagged;
	
	
	
	public String getRole_tagged() {
		return role_tagged;
	}
	public void setRole_tagged(String role_tagged) {
		this.role_tagged = role_tagged;
	}
	public String getBannerId() {
		return bannerId;
	}
	public void setBannerId(String bannerId) {
		this.bannerId = bannerId;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	
}
