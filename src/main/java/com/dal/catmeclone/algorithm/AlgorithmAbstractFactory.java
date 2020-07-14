package com.dal.catmeclone.algorithm;

public interface AlgorithmAbstractFactory {
    public AlgorithmDao createAlgorithmDao();
    public AlgorithmService createAlgorithmService();
}
