package com.dal.catmeclone.adminTest;

import com.dal.catmeclone.UserProfile.UserDao;
import com.dal.catmeclone.admin.*;

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
        return new CourseInstructorAssignmentDaoImpl();
    }

    @Override
    public CourseManagementDao createCourseManagementDao() {
        return new CourseManagementDaoImpl();
    }

}
