package com.dal.catmeclone.DBUtility;

public interface DBUtilityAbstractFactory {
    public DataBaseConnection createDataBaseConnection();
    public PropertiesConfigUtil createPropertiesConfigUtil();
}
