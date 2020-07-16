package com.dal.catmeclone.authenticationTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.authenticationandauthorization.AuthenticateUserDao;
import com.dal.catmeclone.authenticationandauthorization.AuthenticationAbstractFactory;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = UserAuthenticationTest.class)
public class UserAuthenticationTest {
    AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ModelAbstractFactory modelFactory = abstractFactoryTest.createModelAbstractFactory();
    AuthenticationAbstractFactory authenticateFactory = abstractFactoryTest.createAuthenticationAbstractFactory();

    AuthenticateUserDao userAuthentication = authenticateFactory.createAuthenticateUserDao();

    @Test
    void authenticateUserCorrectTest() throws UserDefinedException {
    	
        User user = modelFactory.createUser();
        user.setBannerId("B00832190");
        user.setPassword("Password");
        User authenticatedUser = userAuthentication.authenticateUser(user);
        assertEquals("B00832190", authenticatedUser.getBannerId());
        assertEquals("Guest", authenticatedUser.getUserRoles().getRoleName());
        Assert.assertTrue(new BCryptPasswordEncoder().matches("Password", authenticatedUser.getPassword()));
    }
    
    @Test
    void authenticateUserInCorrectTest() throws UserDefinedException {
    	
        User user = modelFactory.createUser();
        user.setBannerId("B00832190");
        user.setPassword("Password123");
        User authenticatedUser = userAuthentication.authenticateUser(user);
        assertEquals("B00832190", authenticatedUser.getBannerId());
        assertEquals("Guest", authenticatedUser.getUserRoles().getRoleName());
        Assert.assertFalse(new BCryptPasswordEncoder().matches("Password123", authenticatedUser.getPassword()));
    }
}
