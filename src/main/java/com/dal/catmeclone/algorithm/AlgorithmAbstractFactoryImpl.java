package com.dal.catmeclone.algorithm;

import java.util.logging.Logger;

public class AlgorithmAbstractFactoryImpl implements AlgorithmAbstractFactory {

    Logger logger = Logger.getLogger(AlgorithmDaoImpl.class.getName());

    @Override
    public AlgorithmDao createAlgorithmDao() {
        return new AlgorithmDaoImpl();
    }

    @Override
    public GroupFormationStrategy createGroupFormationStrategy(String StrategyName) throws Exception {
        GroupFormationStrategy algorithm = null;
        switch (StrategyName) {
            case "KMeans":
                algorithm = new KMeansStrategy();
                break;
            default:
                logger.warning("Unknown algorithm request : " + StrategyName);
                throw new Exception();
        }
        return algorithm;
    }

    public AlgorithmContext createAlgorithmContext(GroupFormationStrategy groupFormationStrategy) {
        return new AlgorithmContext(groupFormationStrategy);
    }
}
