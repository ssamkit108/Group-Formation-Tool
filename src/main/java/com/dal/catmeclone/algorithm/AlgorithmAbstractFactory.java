package com.dal.catmeclone.algorithm;

public interface AlgorithmAbstractFactory {

    public AlgorithmDao createAlgorithmDao();

    public GroupFormationStrategy createGroupFormationStrategy(String StrategyName) throws Exception;

    public AlgorithmContext createAlgorithmContext(GroupFormationStrategy groupFormationStrategy);
}
