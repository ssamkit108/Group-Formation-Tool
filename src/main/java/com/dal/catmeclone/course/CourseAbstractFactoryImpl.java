package com.dal.catmeclone.course;

public class CourseAbstractFactoryImpl implements CourseAbstractFactory {
    @Override
    public CoursesDao createCourseDao() {
        return new CourseDaoImpl();
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
    public CourseEnrollmentDao createCourseEnrollmentDao() {
        return new CourseEnrollmentDaoImpl();
    }
}
