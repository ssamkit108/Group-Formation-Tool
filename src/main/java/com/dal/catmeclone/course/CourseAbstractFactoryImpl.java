package com.dal.catmeclone.course;

import com.dal.catmeclone.UserProfile.UserDao;

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

	@Override
	public CourseService createCourseService(CoursesDao courseDao) {
		return new CourseServiceImpl(courseDao);
	}

	@Override
	public CourseEnrollmentService createCourseEnrollmentService(UserDao userDB, CourseEnrollmentDao courseEnroll) {
		return new CourseEnrollmentServiceImpl(userDB, courseEnroll);
	}
}
