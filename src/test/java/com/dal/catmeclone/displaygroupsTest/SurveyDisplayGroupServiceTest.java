package com.dal.catmeclone.displaygroupsTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.surveydisplaygroup.SurveyDisplayGroupDao;
import com.dal.catmeclone.surveydisplaygroup.SurveyDisplayGroupService;
import com.dal.catmeclone.surveydisplaygroup.SurveyDisplayGroupServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SurveyDisplayGroupServiceTest {


    AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ModelAbstractFactory modelAbstractFactory = abstractFactoryTest.createModelAbstractFactory();
    SurveyDisplayGroupDao surveyDisplayGroupDao;
    SurveyDisplayGroupService surveyDisplayGroupServiceImpl;

    SurveyDisplayGroupServiceTest() {
        surveyDisplayGroupDao = mock(SurveyDisplayGroupDao.class);
        surveyDisplayGroupServiceImpl = mock(SurveyDisplayGroupServiceImpl.class);
    }

    @BeforeEach
    public void setup() {
        surveyDisplayGroupServiceImpl = new SurveyDisplayGroupServiceImpl(surveyDisplayGroupDao);
    }


    @Test
    void retrievegroupinfoTest() throws UserDefinedException {
        HashMap<String, List<User>> info = new HashMap<>();
        User u1 = modelAbstractFactory.createUser();
        u1.setBannerId("B00109876");
        u1.setEmail("sample123@gmail.com");
        u1.setFirstName("firstname");
        u1.setLastName("lastname");
        User u2 = modelAbstractFactory.createUser();
        u2.setBannerId("B00123456");
        u2.setEmail("example@gmail.com");
        u2.setFirstName("Example");
        u2.setLastName("sample");
        List<User> lst1 = new ArrayList<>();
        lst1.add(u1);
        lst1.add(u2);
        User u3 = modelAbstractFactory.createUser();
        u3.setBannerId("B00143212");
        u3.setEmail("random@gmail.com");
        u3.setFirstName("qwerty");
        u3.setLastName("asdfg");
        List<User> lst2 = new ArrayList<>();
        lst2.add(u3);
        info.put("9", lst2);
        int courseid = 300;
        when(surveyDisplayGroupDao.retrievegroupinfo(courseid)).thenReturn(info);
        assertEquals(surveyDisplayGroupServiceImpl.retrievegroupinfo(courseid).size(), info.size());

    }

    @Test
    void fetchresponseTest() throws UserDefinedException {
        HashMap<String, List<Object>> resp = new HashMap<>();
        List<Object> ans = new ArrayList<>();
        ans.add(3);
        resp.put("Rate yourself in java?", ans);
        List<Object> ans2 = new ArrayList<>();
        ans2.add("Angular");
        resp.put("Which frontend framework are you comfortable?", ans2);
        int courseid = 300;
        String bannerid = "B00123456";
        when(surveyDisplayGroupDao.fetchresponse(courseid, bannerid)).thenReturn(resp);
        assertEquals(surveyDisplayGroupServiceImpl.fetchresponse(courseid, bannerid).size(), resp.size());
    }
}
