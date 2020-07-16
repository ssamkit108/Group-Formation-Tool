package com.dal.catmeclone.algorithm;

import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;


public class AlgorithmContext {

    GroupFormationStrategy groupFormationStrategy;

    AlgorithmContext(GroupFormationStrategy groupFormationStrategy) {
        this.groupFormationStrategy = groupFormationStrategy;
    }

    public Boolean executeStrategy(Course course) throws UserDefinedException, Exception {
        return groupFormationStrategy.formatGroupForCourse(course);
    }
}
