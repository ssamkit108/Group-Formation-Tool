package com.dal.catmeclone.coursesTest;

import com.dal.catmeclone.course.CourseEnrollmentDao;
import com.dal.catmeclone.course.CoursesDao;

public interface CourseAbstractFactoryTest {

	public CoursesDao createCoursesDao();
	public CourseEnrollmentDao createCourseEnrollmentDao();
}
