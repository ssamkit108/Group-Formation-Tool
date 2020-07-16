package com.dal.catmeclone.coursesTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.course.CoursesDao;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.User;
import java.util.ArrayList;

public class CourseDaoMock implements CoursesDao {

    ArrayList<Course> courseList = new ArrayList<Course>();
    AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ModelAbstractFactory modelAbstractFactory = abstractFactoryTest.createModelAbstractFactory();


    public CourseDaoMock() {
        super();
        int courseid = 100;
        String courseName = "Advance Topic in SDC";
        Course course = modelAbstractFactory.createCourse();
        course.setCourseID(courseid);
        course.setCourseName(courseName);
        courseList.add(course);
        Course course2 = modelAbstractFactory.createCourse();
        course2.setCourseID(101);
        course2.setCourseName("Advance topic in web");
        courseList.add(course2);

    }

    @Override
    public Course getCourse(int courseId) {
    	for(Course course: courseList)
    	{
    		if(course.getCourseID()==courseId)
    		{
    			return course;
    		}
    	}
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
