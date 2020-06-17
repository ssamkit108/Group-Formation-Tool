package com.dal.catmeclone.UserProfile;

import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.Validation.ValidatePassword;
import com.dal.catmeclone.Validation.ValidationException;
import com.dal.catmeclone.model.User;
import java.util.UUID;

public class ForgotPasswordServiceImpl implements ForgotPasswordService {

	final Logger logger = LoggerFactory.getLogger(ForgotPasswordServiceImpl.class);

	ForgotPasswordDao forgotpasswordDb;
	ValidatePassword validatepassword;

	public void Resetlink(String username) throws Exception {
		try {
			forgotpasswordDb = SystemConfig.instance().getForgotPasswordDao();
			String token = GenerateToken();

			// This will update token in database and send mail to user
			forgotpasswordDb.UpdateToken(username, token);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public boolean ValidateUser(String username) throws Exception {
		try {
			forgotpasswordDb = SystemConfig.instance().getForgotPasswordDao();
			if (forgotpasswordDb.checkexist(username)) {
				logger.info("Banner Id:" + username + " validated in successfully.");
				return true;
			} else {
				logger.error("User:" + username + " is not validated in forgot password service.");
				throw new Exception("Banner Id:" + username + " does not exist in our system.");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	public String GenerateToken() {
		return UUID.randomUUID().toString();
	}

	@Override
	public String validatetoken(String confirmationToken) throws Exception {
		try {
			forgotpasswordDb = SystemConfig.instance().getForgotPasswordDao();

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
	public void NewPassword(String username, String password) throws Exception {
		try {
			User u = new User();
			u.setBannerId(username);
			u.setPassword(password);
			validatepassword = SystemConfig.instance().getValidatePassword();
			forgotpasswordDb = SystemConfig.instance().getForgotPasswordDao();
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
