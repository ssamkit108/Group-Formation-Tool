package com.dal.catmeclone.Validation;

import java.sql.SQLException;
import java.util.List;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.exceptionhandler.ValidationException;

public class ValidatePassword {
	AbstractFactory abstractFactory=SystemConfig.instance().getAbstractFactory();
	ValidationAbstractFactory validationAbstractFactory=abstractFactory.createValidationAbstractFactory();
	public String recordsErrorMessage;
	private PasswordRulesLoader PasswordValidation;
	final Logger LOGGER = LoggerFactory.getLogger(ValidatePassword.class);

	public void validatepassword(User user) throws ValidationException, UserDefinedSQLException, SQLException {

		PasswordValidation = validationAbstractFactory.createPasswordRulesLoader();
		PasswordValidation.CreateActiveRulesList();
		List<ValidationPolicy> rules = PasswordValidation.getValidationRulesList();
		this.recordsErrorMessage = "";
		for (ValidationPolicy rule : rules) {
			if (!rule.isValid(user)) {
				String error = rule.getError();
				recordsErrorMessage = recordsErrorMessage + "\n" + error;
				LOGGER.error("Registration failed with error : " + error);
			}
		}
		if (!recordsErrorMessage.isEmpty()) {
			throw new ValidationException(recordsErrorMessage);
		}
	}

}
