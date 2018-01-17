package com.zarembin.epampjsp.dao;

import com.zarembin.epampjsp.util.ConnectionDB;

import java.sql.*;

public class AuthenticationDAO {
    public static final String SQL_SELECT_USER =
            "SELECT * FROM cafedb.autуntification_info WHERE user_name =? AND password =?";

    public boolean authenticateUser(String login, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;


        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = ConnectionDB.getConnection();
            preparedStatement  = connection.prepareStatement(SQL_SELECT_USER);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Сonnection close error: " + e);
                }
            }
        }
        return false;
    }
}
