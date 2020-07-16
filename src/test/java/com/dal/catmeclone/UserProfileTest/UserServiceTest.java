package com.dal.catmeclone.UserProfileTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.UserProfile.UserDao;
import com.dal.catmeclone.UserProfile.UserProfileAbstractFactory;
import com.dal.catmeclone.UserProfile.UserService;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.SQLException;

@SpringBootTest
class UserServiceTest {

    AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();
    UserProfileAbstractFactory userProfileAbstractFactoryTestImpl = abstractFactoryTest.createUserProfileAbstractFactory();

    

    @Test
    public void GetAllUsersTest() throws UserDefinedException {
       
        UserDao userDaoMock = userProfileAbstractFactoryTestImpl.createUserDao();
        UserService userService = userProfileAbstractFactoryTestImpl.createUserService(userDaoMock);
        String bannerId="B00825292";

        Assert.assertTrue(userService.findAllMatchingUser(bannerId).size()>0);
    }
    
    @Test
    public void createUserTest() throws SQLException, Exception  {
    	UserDao userDaoMock = userProfileAbstractFactoryTestImpl.createUserDao();
        UserService userService = userProfileAbstractFactoryTestImpl.createUserService(userDaoMock);
        
        User user = modelfact.createUser();
        user.setBannerId("B00825292");
        user.setEmail("bob123@gmail.com");
        user.setFirstName("Bob");
        user.setLastName("Shaw");
        user.setPassword("12345");
        
        Assert.assertTrue(userDaoMock.createUser(user));
    }
}
