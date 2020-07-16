package com.dal.catmeclone.UserProfile;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.Validation.ValidatePassword;
import com.dal.catmeclone.Validation.ValidationAbstractFactory;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.exceptionhandler.ValidationException;
import com.dal.catmeclone.model.User;

import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Logger;

public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    final Logger LOGGER = Logger.getLogger(ForgotPasswordServiceImpl.class.getName());
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    UserProfileAbstractFactory userProfileAbstractFactory = abstractFactory.createUserProfileAbstractFactory();
    ValidationAbstractFactory validationAbstractFactory = abstractFactory.createValidationAbstractFactory();
    ForgotPasswordDao forgotPasswordDao;
    ValidatePassword validatepassword;

    public void resetlink(String username) throws SQLException, Exception {
        forgotPasswordDao = userProfileAbstractFactory.createForgotPasswordDao();
        if (forgotPasswordDao.checkexist(username)) {
            LOGGER.info("Banner Id:" + username + " validated in successfully.");
            String token = generateToken();
            forgotPasswordDao.updateToken(username, token);
            LOGGER.info("Banner Id:" + username + " token generated and sent an link to the user.");
        } else {
            LOGGER.warning("User:" + username + " is not validated in forgot password service.");
            throw new Exception("Banner Id:" + username + " does not exist in our system.");
        }
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String validateToken(String confirmationToken) throws SQLException, Exception {
        forgotPasswordDao = userProfileAbstractFactory.createForgotPasswordDao();
        String bannerid = forgotPasswordDao.checktokenexist(confirmationToken);
        if (!bannerid.isEmpty() && bannerid != null) {
            return bannerid;
        } else {
            return null;
        }
    }

    @Override
    public void setNewPassword(String username, String password) throws UserDefinedSQLException, ValidationException, SQLException, Exception {
        User user = new User();
        user.setBannerId(username);
        user.setPassword(password);
        validatepassword = validationAbstractFactory.createValidatePassword();
        forgotPasswordDao = userProfileAbstractFactory.createForgotPasswordDao();
        validatepassword.validatepassword(user);
        forgotPasswordDao.setNewPassword(username, password);
    }
}
