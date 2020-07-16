package com.dal.catmeclone.adminTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.ModelAbstractFactory;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

@SuppressWarnings("deprecation")
public class CourseTest {

    AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ModelAbstractFactory modelAbstractFactory = abstractFactoryTest.createModelAbstractFactory();

    @Test
    public void setCourseNameTest() {
        Course c = modelAbstractFactory.createCourse();

        c.setCourseName("sdc");
        Assert.isTrue(c.getCourseName().equals("sdc"));
    }

    @Test
    public void getCourseNameTest() {
        Course c = modelAbstractFactory.createCourse();
        c.setCourseName("sdc");
        Assert.isTrue(c.getCourseName().equals("sdc"));
    }

    @Test
    public void setCourseIDTest() {
        Course c = modelAbstractFactory.createCourse();
        c.setCourseID(5408);
        Assert.isTrue(c.getCourseID() == 5408);
    }

    @Test
    public void getCourseIDTest() {
        Course c = modelAbstractFactory.createCourse();
        c.setCourseID(5408);
        Assert.isTrue(c.getCourseID() == 5408);
    }
}
