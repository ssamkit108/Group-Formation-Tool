package com.dal.catmeclone.DBUtility;

public class DBUtilityAbstractFactoryImpl implements DBUtilityAbstractFactory {
    @Override
    public DataBaseConnection createDataBaseConnection() {
        return new DatabaseConnectionImpl();
    }

    @Override
    public PropertiesConfigUtil createPropertiesConfigUtil() {
        return new PropertiesConfigUtil();
    }
}
