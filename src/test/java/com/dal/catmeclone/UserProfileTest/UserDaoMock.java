package com.dal.catmeclone.UserProfileTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.UserProfile.UserDao;
import com.dal.catmeclone.exceptionhandler.DuplicateEntityException;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDaoMock implements UserDao {
    AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ModelAbstractFactory modelFactory = abstractFactoryTest.createModelAbstractFactory();
    List<User> userList;

    public UserDaoMock() {
        userList = new ArrayList<User>();
        User user = modelFactory.createUser();
        user.setBannerId("B00825292");
        user.setFirstName("Bob");
        user.setLastName("Shaw");
        user.setPassword("12345");
        user.setEmail("bob123@gmail.com");
        userList.add(user);
    }

    @Override
    public List<User> findAllMatchingUser(String bannerId) {

        return userList;
    }

    @Override
    public User findUserByBannerID(String bannerId) {
        User user = null;
        for (User userFetched : userList) {
            if (userFetched.getBannerId().equals(bannerId)) {
                user = userFetched;
            }
        }
        return user;
    }

    @Override
    public boolean createUser(User student) throws UserDefinedException, DuplicateEntityException {
        userList.add(student);
        return true;
    }

    @Override
    public List<User> getAllUsers() throws UserDefinedException, Exception {

        return userList;
    }

}
