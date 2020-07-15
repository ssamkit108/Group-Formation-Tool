package com.dal.catmeclone.UserProfile;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.Validation.ValidatePassword;
import com.dal.catmeclone.Validation.ValidationAbstractFactory;
import com.dal.catmeclone.exceptionhandler.ValidationException;
import com.dal.catmeclone.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.UUID;

public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    final Logger LOGGER = LoggerFactory.getLogger(ForgotPasswordServiceImpl.class);
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    UserProfileAbstractFactory userProfileAbstractFactory = abstractFactory.createUserProfileAbstractFactory();
    ValidationAbstractFactory validationAbstractFactory = abstractFactory.createValidationAbstractFactory();
    ForgotPasswordDao forgotpasswordDb;
    ValidatePassword validatepassword;

    public void Resetlink(String username) throws Exception {
        try {
            forgotpasswordDb = userProfileAbstractFactory.createForgotPasswordDao();
            if (forgotpasswordDb.checkexist(username)) {
                LOGGER.info("Banner Id:" + username + " validated in successfully.");
                String token = GenerateToken();
                forgotpasswordDb.UpdateToken(username, token);
                LOGGER.info("Banner Id:" + username + " token generated and sent an link to the user.");
            } else {
                LOGGER.error("User:" + username + " is not validated in forgot password service.");
                throw new Exception("Banner Id:" + username + " does not exist in our system.");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public String GenerateToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String validatetoken(String confirmationToken) throws Exception {
        try {
            forgotpasswordDb = userProfileAbstractFactory.createForgotPasswordDao();
            String bannerid = forgotpasswordDb.checktokenexist(confirmationToken);
            if (!bannerid.isEmpty() && bannerid != null) {
                return bannerid;
            } else {
                return "";
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void setNewPassword(String username, String password) throws Exception {
        try {
            User u = new User();
            u.setBannerId(username);
            u.setPassword(password);
            validatepassword = validationAbstractFactory.createValidatePassword();
            forgotpasswordDb = userProfileAbstractFactory.createForgotPasswordDao();
            validatepassword.validatepassword(u);
            forgotpasswordDb.SetNewPassword(username, password);
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getLocalizedMessage());
        }
    }
}
