package com.dal.catmeclone.algorithm;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import org.springframework.security.core.parameters.P;


public class AlgorithmContext {

    GroupFormationStrategy groupFormationStrategy;
    private AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    private AlgorithmAbstractFactory algorithmAbstractFactory = abstractFactory.createAlgorithmAbstractFactory();

    AlgorithmContext(GroupFormationStrategy groupFormationStrategy) {
        this.groupFormationStrategy = groupFormationStrategy;
    }

    public Boolean executeStrategy(Course course) throws Exception {
        try{
            return groupFormationStrategy.formatGroupForCourse(course);
        }catch (UserDefinedSQLException e){
            throw new UserDefinedSQLException(e.getLocalizedMessage());
        }catch (Exception e){
            throw new Exception(e.getLocalizedMessage());
        }
    }
}
