package com.dal.catmeclone.Validation;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class HistoryConstraintDaoImpl implements HistoryConstraintDao {

    final Logger LOGGER = Logger.getLogger(HistoryConstraintDaoImpl.class.getName());
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    DBUtilityAbstractFactory dbUtilityAbstractFactory = abstractFactory.createDBUtilityAbstractFactory();
    ResultSet resultSet;
    private DataBaseConnection DBUtil;
    private CallableStatement statement;
    private Connection connection;
    private List<String> PasswordList;

    @Override
    public List<String> fetchPasswordList(User u, int limit) throws UserDefinedException, SQLException {
        try {
            PasswordList = new ArrayList<String>();
            DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
            Properties properties = SystemConfig.instance().getProperties();
            connection = DBUtil.connect();
            statement = connection.prepareCall("{call " + properties.getProperty("procedure.get_pwd_history") + "}");
            statement.setString(1, u.getBannerId());
            statement.setInt(2, limit);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                PasswordList.add(resultSet.getString("password"));
            }
            return PasswordList;
        } catch (UserDefinedException e) {
            LOGGER.warning("Error in loading Password History. ");
            throw new UserDefinedException(e.getLocalizedMessage());
        } catch (SQLException e) {
            LOGGER.warning("Error in loading Password History. ");
            throw new SQLException(e.getLocalizedMessage());

        } finally {
            try {
                DBUtil.terminateStatement(statement);
                if (connection != null) {
                    DBUtil.terminateConnection();
                }
            } catch (UserDefinedException e) {
                LOGGER.warning("Error in loading Password History. ");
                throw new UserDefinedException(e.getLocalizedMessage());
            }

        }

    }
}
