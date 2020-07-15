package com.dal.catmeclone.Validation;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class HistoryConstraintDaoImpl implements HistoryConstraintDao {

    final Logger LOGGER = LoggerFactory.getLogger(HistoryConstraintDaoImpl.class);
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    DBUtilityAbstractFactory dbUtilityAbstractFactory = abstractFactory.createDBUtilityAbstractFactory();
    ResultSet rs;
    private DataBaseConnection DBUtil;
    private CallableStatement statement;
    private Connection connection;
    private List<String> PasswordList;

    @Override
    public List<String> fetchPasswordList(User u, int limit) throws UserDefinedSQLException, SQLException {
        try {
            PasswordList = new ArrayList<String>();
            DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
            Properties properties = SystemConfig.instance().getProperties();
            connection = DBUtil.connect();
            statement = connection.prepareCall("{call " + properties.getProperty("procedure.get_pwd_history") + "}");
            statement.setString(1, u.getBannerId());
            statement.setInt(2, limit);
            rs = statement.executeQuery();

            while (rs.next()) {
                PasswordList.add(rs.getString("password"));
            }
            return PasswordList;
        } catch (UserDefinedSQLException e) {
            LOGGER.error("Error in loading Password History. ", e);
            throw new UserDefinedSQLException(e.getLocalizedMessage());
        } catch (SQLException e) {
            LOGGER.error("Error in loading Password History. ", e);
            throw new SQLException(e.getLocalizedMessage());

        } finally {
            try {
                DBUtil.terminateStatement(statement);
                if (connection != null) {
                    DBUtil.terminateConnection();
                }
            } catch (UserDefinedSQLException e) {
                LOGGER.error("Error in loading Password History. ", e);
            }

        }

    }
}
