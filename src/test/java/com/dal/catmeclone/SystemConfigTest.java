package com.dal.catmeclone;

public class SystemConfigTest {

    private static SystemConfigTest uniqueInstance;
    private AbstractFactory abstractFactoryTest;

    public SystemConfigTest() {
        super();
        this.abstractFactoryTest = new AbstractFactoryTestImpl();
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

    public AbstractFactory getAbstractFactoryTest() {
        return abstractFactoryTest;
    }

    public void setAbstractFactoryTest(AbstractFactory abstractFactoryTest) {
        this.abstractFactoryTest = abstractFactoryTest;
    }
}
