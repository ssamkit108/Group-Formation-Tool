package com.dal.catmeclone.course;

public interface CourseAbstractFactory {
    public CoursesDao createCourseDao();
    public CourseService createCourseService();
    public CourseEnrollmentService createCourseEnrollmentService();
    public CourseEnrollmentDao createCourseEnrollmentDao();
}
