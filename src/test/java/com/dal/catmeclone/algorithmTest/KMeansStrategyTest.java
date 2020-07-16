package com.dal.catmeclone.algorithmTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.algorithm.AlgorithmDao;
import com.dal.catmeclone.algorithm.KMeansStrategy;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.ModelAbstractFactory;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class KMeansStrategyTest {

	AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();
    final AlgorithmDao algorithmDaoImpl;
    KMeansStrategy kmeansStrategy;
    
    public KMeansStrategyTest() {
    	algorithmDaoImpl = mock(AlgorithmDao.class);
    	kmeansStrategy = mock(KMeansStrategy.class);	
    }
    
    @BeforeEach
    public void setup() {
    	kmeansStrategy = new KMeansStrategy(algorithmDaoImpl);
    }
    
    @Test
    public void formatGroupsForCourseTest() throws Exception{
    	Course course = modelfact.createCourse();
        course.setCourseID(123);
        List<List<String>> groups = new ArrayList<>();
        List<String> firstGroup = new ArrayList<String>();
        groups.add(firstGroup);
        when(algorithmDaoImpl.updateGroupsFormed(groups, course.getCourseID())).thenReturn(true);
        assertTrue(kmeansStrategy.formatGroupForCourse(course));
    }
}
