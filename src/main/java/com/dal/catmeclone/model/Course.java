package com.dal.catmeclone.model;

public class Course {

	private int courseID;
	private String courseName;
	private User courseInstructor;
	
	
	/**
	 * 
	 */
	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param courseID
	 * @param courseName
	 */
	public Course(int courseID, String courseName) {
		super();
		this.courseID = courseID;
		this.courseName = courseName;
	}


	/**
	 * @return the courseID
	 */
	public int getCourseID() {
		return courseID;
	}


	/**
	 * @param courseID the courseID to set
	 */
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}


	/**
	 * @return the courseName
	 */
	public String getCourseName() {
		return courseName;
	}


	/**
	 * @param courseName the courseName to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + courseID;
		result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
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
		Course other = (Course) obj;
		if (courseID != other.courseID)
			return false;
		if (courseName == null) {
			if (other.courseName != null)
				return false;
		} else if (!courseName.equals(other.courseName))
			return false;
		return true;
	}
	
	
	
	
}
