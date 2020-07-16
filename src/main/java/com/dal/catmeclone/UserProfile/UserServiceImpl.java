package com.dal.catmeclone.UserProfile;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.Validation.ValidatePassword;
import com.dal.catmeclone.Validation.ValidationAbstractFactory;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserServiceImpl implements UserService {

    private final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    UserProfileAbstractFactory userProfileAbstractFactory = abstractFactory.createUserProfileAbstractFactory();
    ValidationAbstractFactory validationAbstractFactory = abstractFactory.createValidationAbstractFactory();
    ValidatePassword validatePassword;
    UserDao userDao;
    Boolean flag = false;
    
    public UserServiceImpl() {
		super();
		this.userDao = userProfileAbstractFactory.createUserDao();
	}
    
	public UserServiceImpl(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	@Override
    public boolean createUser(User user) throws SQLException, Exception {
        validatePassword = validationAbstractFactory.createValidatePassword();
        validatePassword.validatepassword(user);
        LOGGER.info("Accessing DAO layer to create user to given banner id" + user.getBannerId());
        flag = userDao.createUser(user);
        return flag;
    }

    @Override
    public List<User> findAllMatchingUser(String bannerId) throws UserDefinedException {
        List<User> listOfUser = new ArrayList<User>();
        LOGGER.info("Accessing DAO layer to get matching list of user to given banner id");
        listOfUser = userDao.findAllMatchingUser(bannerId);
        return listOfUser;
    }
}
