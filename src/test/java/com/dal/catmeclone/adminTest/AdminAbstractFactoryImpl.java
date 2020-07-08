package com.dal.catmeclone.adminTest;

import com.dal.catmeclone.admin.CourseInstructorAssignmentDao;
import com.dal.catmeclone.admin.CourseManagementDao;

public class AdminAbstractFactoryImpl implements AdminAbstractFactoryTest{

	@Override
	public CourseInstructorAssignmentDao createCourseInstructorAssignmentDao() {
		// TODO Auto-generated method stub
		return new CourseInstructorAssignmentMock();
	}

	@Override
	public CourseManagementDao createCourseManagementDao() {
		// TODO Auto-generated method stub
		return new CourseMock();
	}

}
