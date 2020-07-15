package com.dal.catmeclone.coursesTest;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.course.CoursesDao;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;

import java.util.ArrayList;

public class CourseDaoMock implements CoursesDao {

    int courseid;
    String courseName;
    ArrayList<Course> courseList = new ArrayList<Course>();
    IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
    IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();


    public CourseDaoMock() {
        super();
        this.courseid = 100;
        this.courseName = "Advance Topic in SDC";
        Course crc = modelfact.createCourse();
        crc.setCourseID(courseid);
        crc.setCourseName(courseName);
        courseList.add(crc);
        Course crc2 = modelfact.createCourse();
        crc2.setCourseID(101);
        crc2.setCourseName("Advance topic in web");
        courseList.add(crc2);

    }

    @Override
    public Course getCourse(int courseId) {

        if (courseId == this.courseid) {
            Course crc = modelfact.createCourse();
            crc.setCourseID(courseid);
            crc.setCourseName(courseName);
            return crc;
        } else
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
