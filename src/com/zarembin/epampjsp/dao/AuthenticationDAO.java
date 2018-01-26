package com.zarembin.epampjsp.dao;

import com.zarembin.epampjsp.entity.User;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.proxy.ConnectionPool;
import com.zarembin.epampjsp.proxy.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationDAO {
    private final static String SQL_SELECT_USER =
            "SELECT user_name,password,is_admin,is_ban,name,last_name,loyalty_points,money,`e-mail`,number_of_orders,card_number FROM cafedb.personal_info WHERE user_name=? AND password =?";

    public User findUser(String login, String password) throws DAOException {
        User user = null;
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement  = connection.prepareStatement(SQL_SELECT_USER);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(resultSet.getString(1), resultSet.getString(2),
                        "1".equals(resultSet.getString(3)),"1".equals(resultSet.getString(4)),
                        resultSet.getString(5),resultSet.getString(6),resultSet.getInt(7),
                        resultSet.getBigDecimal(8),resultSet.getString(9),resultSet.getInt(10),
                        resultSet.getString(11));
                return user;

            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(),e.getCause());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            }
        }
        return user;
    }
}
