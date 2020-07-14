package com.dal.catmeclone.coursesTest;

import com.dal.catmeclone.course.CourseEnrollmentDao;
import com.dal.catmeclone.course.CoursesDao;

public interface ICourseAbstractFactory {

	public CoursesDao createCoursesDao();
	public CourseEnrollmentDao createCourseEnrollmentDao();
}
