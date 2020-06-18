package com.dal.catmeclone.courses;

import java.util.ArrayList;
import com.dal.catmeclone.course.CoursesDao;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;

public class CourseDaoMock implements CoursesDao {

	int courseid;
	String courseName;
	ArrayList<Course> courseList = new ArrayList<Course>();

	public CourseDaoMock() {
		super();
		this.courseid = 100;
		this.courseName = "Advance Topic in SDC";
		courseList.add(new Course(courseid, courseName));
		courseList.add(new Course(101, "Advance topic in web"));

	}

	@Override
	public Course getCourse(int courseId) {

		if (courseId == this.courseid)
			return new Course(courseid, courseName);
		else
			return null;
	}

	@Override
	public ArrayList<Course> getallcourses() {

		return courseList;
	}

	@Override
	public ArrayList<Course> getallcoursesbyuser(User user) {
		if (user.getBannerId().equals("B00839818")) {
			return courseList;
		} else
			return null;
	}

}
