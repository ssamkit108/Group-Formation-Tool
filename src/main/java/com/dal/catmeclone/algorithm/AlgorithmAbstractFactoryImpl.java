package com.dal.catmeclone.algorithm;

import java.util.logging.Logger;

public class AlgorithmAbstractFactoryImpl implements AlgorithmAbstractFactory {

    Logger LOGGER = Logger.getLogger(AlgorithmAbstractFactoryImpl.class.getName());

    @Override
    public AlgorithmDao createAlgorithmDao() {
        return new AlgorithmDaoImpl();
    }

    @Override
    public GroupFormationStrategy createGroupFormationStrategy(String StrategyName) {
        GroupFormationStrategy algorithm = null;
        switch (StrategyName) {
            case "KMeans":
                algorithm = new KMeansStrategy();
                break;
            default:
                LOGGER.warning("Unknown algorithm request : " + StrategyName);
                break;
        }
        return algorithm;
    }

    public AlgorithmContext createAlgorithmContext(GroupFormationStrategy groupFormationStrategy) {
        return new AlgorithmContext(groupFormationStrategy);
    }
}
