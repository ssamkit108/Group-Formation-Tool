package com.dal.catmeclone.coursesTest;

import com.dal.catmeclone.course.CourseEnrollmentDao;
import com.dal.catmeclone.course.CoursesDao;

public class ICourseAbstractFactoryImpl implements ICourseAbstractFactory{

	@Override
	public CoursesDao createCoursesDao() {
		// TODO Auto-generated method stub
		return new CourseDaoMock();
	}

	@Override
	public CourseEnrollmentDao createCourseEnrollmentDao() {
		// TODO Auto-generated method stub
		return new CourseEnrollmentDaoMock();
	}

}
