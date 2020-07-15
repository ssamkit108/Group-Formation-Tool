package com.dal.catmeclone.coursesTest;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.course.CoursesDao;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;

public class CourseServiceTest {
    IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
    ICourseAbstractFactory mock = abstractFactoryTest.createCourseAbstractFactory();
    IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();

    @Test
    void getallcoursesbyuserTest() throws UserDefinedSQLException {
        User user = modelfact.createUser();
        user.setBannerId("B00839818");
        ArrayList<Course> courselst = new ArrayList<Course>();
        CoursesDao courseDBmock = mock.createCoursesDao();
        courselst = courseDBmock.getallcoursesbyuser(user);
        assertNotNull(courselst);
    }

    @Test
    public void getallcoursesTest() throws SQLException, UserDefinedSQLException {
        CoursesDao courseDBmock = mock.createCoursesDao();
        assertNotNull(courseDBmock.getallcourses());
    }

}
