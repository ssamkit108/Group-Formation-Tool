package com.dal.catmeclone.course;

import com.dal.catmeclone.UserProfile.UserDao;

public interface CourseAbstractFactory {
    public CoursesDao createCourseDao();

    public CourseService createCourseService();

    public CourseEnrollmentService createCourseEnrollmentService();

    public CourseEnrollmentService createCourseEnrollmentService(UserDao userDB, CourseEnrollmentDao courseEnroll);

    public CourseEnrollmentDao createCourseEnrollmentDao();

    public CourseService createCourseService(CoursesDao courseDao);

}
