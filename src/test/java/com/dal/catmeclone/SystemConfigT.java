package com.dal.catmeclone;

public class SystemConfigT {

	private IAbstractFactory abstractFactoryTest;
	private static SystemConfigT uniqueInstance;

	public SystemConfigT() {
		super();
		this.abstractFactoryTest = new IAbstractFactoryImpl();
	}

	public IAbstractFactory getAbstractFactoryTest() {
		return abstractFactoryTest;
	}

	public void setAbstractFactoryTest(IAbstractFactory abstractFactoryTest) {
		this.abstractFactoryTest = abstractFactoryTest;
	}

	// This is the way the rest of the application gets access to the System object.
	public static SystemConfigT instance() {
		// Using lazy initialization, this is the one and only place that the System
		// object will be instantiated.
		if (null == uniqueInstance) {
			uniqueInstance = new SystemConfigT();
		}
		return uniqueInstance;
	}
}
