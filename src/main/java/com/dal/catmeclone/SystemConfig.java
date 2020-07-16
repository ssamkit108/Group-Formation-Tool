package com.dal.catmeclone;

import com.dal.catmeclone.DBUtility.PropertiesConfigUtil;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class SystemConfig {

	private final Logger LOGGER = Logger.getLogger(SystemConfig.class.getName());
	private static SystemConfig uniqueInstance = null;

	private String resourceFilename = "application.properties";

	private Properties properties;
	private PropertiesConfigUtil propertiesConfig;
	private AbstractFactory abstractFactory;

	public SystemConfig() {
		super();
		this.abstractFactory = new AbstractFactoryImpl();
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

	public static SystemConfig getUniqueInstance() {
		return uniqueInstance;
	}

	public static void setUniqueInstance(SystemConfig uniqueInstance) {
		SystemConfig.uniqueInstance = uniqueInstance;
	}

	public Properties initializProperties(PropertiesConfigUtil propertiesConfig) {
		Properties property = null;
		try {
			property = PropertiesConfigUtil.loadProperties(resourceFilename);
		} catch (IOException e) {
			LOGGER.warning("Error Occured While Reading " + resourceFilename + "");
		}
		return property;
	}

	public String getResourceFilename() {
		return resourceFilename;
	}

	public void setResourceFilename(String resourceFilename) {
		this.resourceFilename = resourceFilename;
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
