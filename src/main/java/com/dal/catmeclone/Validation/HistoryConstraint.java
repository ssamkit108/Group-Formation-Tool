package com.dal.catmeclone.Validation;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.encrypt.BCryptPasswordEncryption;
import com.dal.catmeclone.encrypt.EncryptAbstractFactory;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class HistoryConstraint implements ValidationPolicy {

    final Logger LOGGER = Logger.getLogger(HistoryConstraint.class.getName());
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    ValidationAbstractFactory validationAbstractFactory = abstractFactory.createValidationAbstractFactory();
    EncryptAbstractFactory encryptAbstractFactory = abstractFactory.createEncryptAbstractFactory();
    HistoryConstraintDao historyConstraintDao;
    private BCryptPasswordEncryption passwordencoder;
    private String ruleValue;
    private List<String> passwordlist;

    public void setValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }

    public boolean isValid(User user) throws UserDefinedException, SQLException, Exception, NumberFormatException {
        try {
            boolean result = false;
            int limit = Integer.parseInt(ruleValue);
            historyConstraintDao = validationAbstractFactory.createHistoryConstraintDao();
            passwordencoder = encryptAbstractFactory.createBCryptPasswordEncryption();
            passwordlist = historyConstraintDao.fetchPasswordList(user, limit);
            for (String password : passwordlist) {
                result = passwordencoder.matches(user.getPassword(), password);
                if (result) {
                    LOGGER.info("Does Password matched in the history Result : " + result);
                    return !result;
                }
            }
            LOGGER.info("Does Password matched in the history. Result : " + result);
            return !result;
        } catch (UserDefinedException e) {
            LOGGER.warning("Error in loading Password History. ");
            throw new UserDefinedException(e.getLocalizedMessage());
        } catch (SQLException e) {
            LOGGER.warning("Error in loading Password History. ");
            throw new SQLException(e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            LOGGER.warning("Error in loading Password History. ");
            throw new NumberFormatException(e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.warning("Error in loading Password History. ");
            throw new Exception(e.getLocalizedMessage());
        }
    }

    @Override
    public String getError() {
        return "Password cannot be same as past " + this.ruleValue + " passwords";
    }
}
