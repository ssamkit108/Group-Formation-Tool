package com.dal.catmeclone;
import com.dal.catmeclone.AbstractFactoryImpl;
import com.dal.catmeclone.SystemConfig;

public class SystemConfigTest {

	private AbstractFactoryTest abstractFactoryTest;
	private static SystemConfigTest uniqueInstance;

	public SystemConfigTest() {
		super();
		this.abstractFactoryTest = new AbstractFactoryTestImpl();
	}

	public AbstractFactoryTest getAbstractFactoryTest() {
		return abstractFactoryTest;
	}

	public void setAbstractFactoryTest(AbstractFactoryTest abstractFactoryTest) {
		this.abstractFactoryTest = abstractFactoryTest;
	}

	// This is the way the rest of the application gets access to the System object.
	public static SystemConfigTest instance() {
		// Using lazy initialization, this is the one and only place that the System
		// object will be instantiated.
		if (null == uniqueInstance) {
			uniqueInstance = new SystemConfigTest();
		}
		return uniqueInstance;
	}
}
