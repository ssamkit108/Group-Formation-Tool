package com.dal.catmeclone.authenticationandauthorization;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

@Component
public class AuthenticateUserDaoImpl implements AuthenticateUserDao {

    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    DBUtilityAbstractFactory dbUtilityAbstractFactory = abstractFactory.createDBUtilityAbstractFactory();
    DataBaseConnection DBUtil;
    private CallableStatement statement;
    private Connection connection;

    @Override
    public User authenticateUser(User currentUser) throws UserDefinedSQLException {
        User user = null;
        Properties property = SystemConfig.instance().getProperties();
        String authenticateUser = property.getProperty("procedure.authenticateUser");
        try {
            DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
            connection = DBUtil.connect();
            statement = connection.prepareCall("{call " + authenticateUser + "}");

            statement.setString(1, currentUser.getBannerId());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                user = new User();
                user.setBannerId(result.getString("bannerId"));
                user.setPassword(result.getString("password"));
                user.setUserRoles(new Role(result.getInt("roleid"), result.getString("rolename")));
            }
        } catch (SQLException e) {
            throw new UserDefinedSQLException("Some error occured");
        } finally {
            DBUtil.terminateStatement(statement);
            if (connection != null)
                DBUtil.terminateConnection();
        }
        return user;
    }
}