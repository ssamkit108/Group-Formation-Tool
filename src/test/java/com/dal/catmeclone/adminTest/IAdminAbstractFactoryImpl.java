package com.dal.catmeclone.adminTest;

import com.dal.catmeclone.admin.CourseInstructorAssignmentDao;
import com.dal.catmeclone.admin.CourseManagementDao;

public class IAdminAbstractFactoryImpl implements IAdminAbstractFactory {

    @Override
    public CourseInstructorAssignmentDao createCourseInstructorAssignmentDao() {
        return new CourseInstructorAssignmentMock();
    }

    @Override
    public CourseManagementDao createCourseManagementDao() {
        return new CourseMock();
    }

}
