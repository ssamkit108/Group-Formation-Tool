package com.dal.catmeclone;

import java.io.IOException;
import java.util.Properties;
import org.springframework.core.env.Environment;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.DBUtility.DatabaseConnectionImpl;
import com.dal.catmeclone.DBUtility.PropertiesConfigUtil;

public class SystemConfig {

	private static SystemConfig uniqueInstance = null;

	private String resourceFilename = "application.properties";

    private Properties properties;
	private Environment env;
	private PropertiesConfigUtil propertiesConfig;
    private AbstractFactory abstractFactory;

    public SystemConfig() {
        super();
        this.abstractFactory=new AbstractFactoryImpl();
        this.properties = initializProperties(propertiesConfig);
    }

    // This is the way the rest of the application gets access to the System object.
    public static SystemConfig instance() {
        // Using lazy initialization, this is the one and only place that the System
        // object will be instantiated.
        if (null == uniqueInstance) {
            uniqueInstance = new SystemConfig();
        }
        return uniqueInstance;
    }

    public Properties initializProperties(PropertiesConfigUtil propertiesConfig) {
        Properties property = null;
        try {

            property = PropertiesConfigUtil.loadProperties(resourceFilename);

        } catch (IOException e) {

            e.printStackTrace();
        }
        return property;
    }

    public static SystemConfig getUniqueInstance() {
        return uniqueInstance;
    }

    public static void setUniqueInstance(SystemConfig uniqueInstance) {
        SystemConfig.uniqueInstance = uniqueInstance;
    }

    public String getResourceFilename() {
        return resourceFilename;
    }

    public void setResourceFilename(String resourceFilename) {
        this.resourceFilename = resourceFilename;
    }

    public Environment getEnv() {
        return env;
    }

    public void setEnv(Environment env) {
        this.env = env;
    }

    public PropertiesConfigUtil getPropertiesConfig() {
        return propertiesConfig;
    }

    public void setPropertiesConfig(PropertiesConfigUtil propertiesConfig) {
        this.propertiesConfig = propertiesConfig;
    }

    public AbstractFactory getAbstractFactory() {
        return abstractFactory;
    }

    public void setAbstractFactory(AbstractFactory abstractFactory) {
        this.abstractFactory = abstractFactory;
    }


	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}


}
