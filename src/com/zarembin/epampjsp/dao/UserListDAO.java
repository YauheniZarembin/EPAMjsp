package com.zarembin.epampjsp.dao;

import com.zarembin.epampjsp.entity.User;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.proxy.ConnectionPool;
import com.zarembin.epampjsp.proxy.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserListDAO {
    private final static String SQL_SELECT_USERS =
            "SELECT user_name,password,is_admin,is_ban,name,last_name,loyalty_points,money,`e-mail`,number_of_orders,card_number FROM cafedb.personal_info;";

    private final static String SQL_UPDATE_USER_BAN =
            "UPDATE cafedb.personal_info SET is_ban=? WHERE user_name=?";

    private final static String SQL_SELECT_POINTS =
            "SELECT loyalty_points FROM cafedb.personal_info WHERE user_name=?;";

    private final static String SQL_UPDATE_USER_BAN_AND_POINTS=
            "UPDATE cafedb.personal_info SET is_ban=?, loyalty_points=? WHERE user_name=?;";


    public void changeUserBan(String userName, String banString) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_BAN);
            preparedStatement.setString(1,banString);
            preparedStatement.setString(2,userName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            }
        }
    }

    public int findUserPoints(String userName) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_POINTS);
            preparedStatement.setString(1,userName);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            }
        }
    }


    public List<User> findAllUsers() throws DAOException {

        List<User> usersList = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_USERS);
            while (resultSet.next()) {
                usersList.add(new User(resultSet.getString(1), resultSet.getString(2),
                        "1".equals(resultSet.getString(3)),"1".equals(resultSet.getString(4)),
                        resultSet.getString(5),resultSet.getString(6),resultSet.getInt(7),
                        resultSet.getBigDecimal(8),resultSet.getString(9),resultSet.getInt(10),
                        resultSet.getString(11)));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            }
        }
        return usersList;
    }

    public void deleteLoyaltyPoint(String ban, int loyaltyPoints, String userName) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_BAN_AND_POINTS);
            preparedStatement.setString(1,ban);
            preparedStatement.setString(2,String.valueOf(loyaltyPoints));
            preparedStatement.setString(3,userName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            }
        }

    }
}
