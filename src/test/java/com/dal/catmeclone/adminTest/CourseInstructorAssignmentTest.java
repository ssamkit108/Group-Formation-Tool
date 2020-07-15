package com.dal.catmeclone.adminTest;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.admin.CourseInstructorAssignmentDao;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.sql.SQLException;

public class CourseInstructorAssignmentTest {
    IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
    IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();

    @SuppressWarnings("deprecation")
    @Test
    public void insertCourseTest() throws UserDefinedSQLException, SQLException {
        Course c = modelfact.createCourse();
        c.setCourseID(123);
        User u = modelfact.createUser();
        u.setBannerId("B00833211");
        Role r = modelfact.createRole();
        r.setRoleName("Instructor");

        CourseInstructorAssignmentDao mock = abstractFactoryTest.createAdminAbstractFactory()
                .createCourseInstructorAssignmentDao();
        Assert.isTrue(mock.enrollInstructorForCourse(u, c, r));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void checkInstructorForCourseTest() throws UserDefinedSQLException, SQLException {
        Course c = modelfact.createCourse();
        CourseInstructorAssignmentDao mock = abstractFactoryTest.createAdminAbstractFactory()
                .createCourseInstructorAssignmentDao();
        c.setCourseID(123);
        c.setCourseName("sdc");
        Assert.isTrue(mock.checkInstructorForCourse(new Course(c.getCourseID(), c.getCourseName())));
    }
}
