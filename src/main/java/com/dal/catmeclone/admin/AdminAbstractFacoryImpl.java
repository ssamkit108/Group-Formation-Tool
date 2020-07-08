package com.dal.catmeclone.admin;

public class AdminAbstractFacoryImpl implements AdminAbstractFactory {
    @Override
    public AdminService createAdminService() {
        return new AdminServiceImpl();
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
