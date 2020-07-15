package com.dal.catmeclone.DBUtility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesConfigUtil {

    public static Properties loadProperties(String resourceFileName) throws IOException {
        Properties configuration = new Properties();
        InputStream inputStream = PropertiesConfigUtil.class
                .getClassLoader()
                .getResourceAsStream(resourceFileName);
        configuration.load(inputStream);
        inputStream.close();
        return configuration;
    }


}
