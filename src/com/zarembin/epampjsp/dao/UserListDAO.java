package com.zarembin.epampjsp.dao;

import com.zarembin.epampjsp.entity.Dish;
import com.zarembin.epampjsp.entity.User;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.proxy.ConnectionPool;
import com.zarembin.epampjsp.proxy.ProxyConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserListDAO {
    private final String SQL_SELECT_USERS =
            "SELECT user_name,password,is_admin,is_ban,name,last_name,loyalty_points,money,`e-mail`,number_of_orders,card_number FROM cafedb.personal_info;";


    public List<User> findAllUsers() throws DAOException {

        List<User> listUsers = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_USERS);
            while (resultSet.next()) {
                listUsers.add(new User(resultSet.getString(1), resultSet.getString(2),
                        "1".equals(resultSet.getString(3)),"1".equals(resultSet.getString(4)),
                        resultSet.getString(5),resultSet.getString(6),resultSet.getInt(7),
                        resultSet.getBigDecimal(8),resultSet.getString(9),resultSet.getInt(10),
                        resultSet.getString(11)));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Сonnection close error: " + e);
                }
            }
        }
        return listUsers;
    }
}
