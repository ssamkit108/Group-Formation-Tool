package com.dal.catmeclone.adminTest;

import com.dal.catmeclone.UserProfile.UserDao;
import com.dal.catmeclone.admin.AdminAbstractFactory;
import com.dal.catmeclone.admin.AdminService;
import com.dal.catmeclone.admin.CourseInstructorAssignmentDao;
import com.dal.catmeclone.admin.CourseManagementDao;

public class AdminAbstractFactoryTestImpl implements AdminAbstractFactory {

	@Override
	public AdminService createAdminService() {
		return new com.dal.catmeclone.admin.AdminServiceImpl();
	}

	@Override
	public AdminService AdminServiceImpl(CourseInstructorAssignmentDao courseInstructor,
			CourseManagementDao courseManagement, UserDao userDao) {

		return new com.dal.catmeclone.admin.AdminServiceImpl(courseInstructor, courseManagement, userDao);
	}

	@Override
	public CourseInstructorAssignmentDao createCourseInstructorAssignmentDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CourseManagementDao createCourseManagementDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
