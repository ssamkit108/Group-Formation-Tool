package com.dal.catmeclone.algorithm;

public class AlgorithmAbstractFactoryImpl implements AlgorithmAbstractFactory{
    @Override
    public AlgorithmDao createAlgorithmDao() {
        return new AlgorithmDaoImpl();
    }

    @Override
    public AlgorithmService createAlgorithmService() {
        return new AlgorithmServiceImpl();
    }
}
