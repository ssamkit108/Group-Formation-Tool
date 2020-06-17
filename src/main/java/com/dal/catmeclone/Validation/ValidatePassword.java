package com.dal.catmeclone.Validation;

import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;

public class ValidatePassword {
	public String listoferror;
	private PasswordRulesLoader PasswordValidation;
	final Logger logger = LoggerFactory.getLogger(ValidatePassword.class);

	public void validatepassword(User user) throws ValidationException, UserDefinedSQLException, SQLException {

		PasswordValidation = SystemConfig.instance().getPasswordRules();
		PasswordValidation.CreateActiveRulesList();
		List<ValidationPolicy> rules = PasswordValidation.getValidationRulesList();
		this.listoferror = "";
		for (ValidationPolicy rule : rules) {
			if (!rule.isValid(user)) {
				String error = rule.getError();
				listoferror = listoferror + "\n" + error;
				logger.error("Registration failed with error : " + error);
			}
		}
		if (!listoferror.isEmpty()) {
			throw new ValidationException(listoferror);
		}
	}

}
