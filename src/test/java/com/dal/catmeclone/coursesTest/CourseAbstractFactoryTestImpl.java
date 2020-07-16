package com.dal.catmeclone.coursesTest;

import com.dal.catmeclone.UserProfile.UserDao;
import com.dal.catmeclone.course.*;

public class CourseAbstractFactoryTestImpl implements CourseAbstractFactory {

    @Override
    public CourseEnrollmentDao createCourseEnrollmentDao() {

        return new CourseEnrollmentDaoMock();
    }

    @Override
    public CoursesDao createCourseDao() {
        return new CourseDaoMock();
    }

    @Override
    public CourseService createCourseService() {

        return new CourseServiceImpl();
    }

    @Override
    public CourseEnrollmentService createCourseEnrollmentService() {

        return new CourseEnrollmentServiceImpl();
    }

    @Override
    public CourseService createCourseService(CoursesDao courseDao) {
        return new CourseServiceImpl(courseDao);
    }

    @Override
    public CourseEnrollmentService createCourseEnrollmentService(UserDao userDB, CourseEnrollmentDao courseEnroll) {
        return new CourseEnrollmentServiceImpl(userDB, courseEnroll);
    }

}
