package com.dal.catmeclone.courses;

import java.sql.SQLException;
import java.util.ArrayList;
import com.dal.catmeclone.course.CoursesDao;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;

public class CourseDaoMock implements CoursesDao {

	int courseid;
	String courseName;
	ArrayList<Course>  courseList  = new ArrayList<Course>();
	
	
	
	

	public CourseDaoMock() {
		super();
		this.courseid =100;
		this.courseName="Advance Topic in SDC";
		courseList.add(new Course(courseid, courseName));
		courseList.add(new Course(101, "Advance topic in web"));
		
	
	}



	@Override
	public Course getCourse(int courseId) {
		// TODO Auto-generated method stub
		if(courseId==this.courseid) return new Course(courseid,courseName);
		else return null;
	}



	@Override
	public ArrayList<Course> getallcourses(){
		// TODO Auto-generated method stub
		return courseList;
	}
	
	@Override
	public ArrayList<Course> getallcoursesbyuser(User user){
		if(user.getBannerId().equals("B00839818"))
		{
			return courseList;
		}
		else return null;
	}


	

}
