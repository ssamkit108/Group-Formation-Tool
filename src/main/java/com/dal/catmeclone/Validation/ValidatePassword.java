package com.dal.catmeclone.Validation;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.exceptionhandler.ValidationException;
import com.dal.catmeclone.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class ValidatePassword {
    final Logger LOGGER = Logger.getLogger(ValidatePassword.class.getName());
    public String recordsErrorMessage;
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    ValidationAbstractFactory validationAbstractFactory = abstractFactory.createValidationAbstractFactory();
    private PasswordRulesLoader PasswordValidation;

    public void validatepassword(User user) throws ValidationException, UserDefinedSQLException, SQLException, Exception {

        PasswordValidation = validationAbstractFactory.createPasswordRulesLoader();
        PasswordValidation.CreateActiveRulesList();
        List<ValidationPolicy> rules = PasswordValidation.getValidationRulesList();
        this.recordsErrorMessage = "";
        for (ValidationPolicy rule : rules) {
            if (rule.isValid(user) == false) {
                String error = rule.getError();
                recordsErrorMessage = recordsErrorMessage + "\n" + error;
                LOGGER.warning("Registration failed with error : " + error);
            }
        }
        if (!recordsErrorMessage.isEmpty()) {
            throw new ValidationException(recordsErrorMessage);
        }
    }

}
