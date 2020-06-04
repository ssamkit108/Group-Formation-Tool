package com.dal.catmeclone.courses;

import com.dal.catmeclone.course.CoursesDao;
import com.dal.catmeclone.model.Course;

public class CourseDaoMock implements CoursesDao {

	int courseid;
	String courseName;
	
	

	public CourseDaoMock() {
		super();
		this.courseid =100;
		this.courseName="Advance Topic in SDC";
	}



	@Override
	public Course getCourse(int courseId) {
		// TODO Auto-generated method stub
		if(courseId==this.courseid) return new Course(courseid,courseName);
		else return null;
	}

}
