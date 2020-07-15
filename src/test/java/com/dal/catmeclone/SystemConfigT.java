package com.dal.catmeclone;

public class SystemConfigT {

    private static SystemConfigT uniqueInstance;
    private IAbstractFactory abstractFactoryTest;

    public SystemConfigT() {
        super();
        this.abstractFactoryTest = new IAbstractFactoryImpl();
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

    public IAbstractFactory getAbstractFactoryTest() {
        return abstractFactoryTest;
    }

    public void setAbstractFactoryTest(IAbstractFactory abstractFactoryTest) {
        this.abstractFactoryTest = abstractFactoryTest;
    }
}
