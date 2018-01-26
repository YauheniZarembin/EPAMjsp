package com.zarembin.epampjsp.dao;

import com.zarembin.epampjsp.entity.Order;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.proxy.ConnectionPool;
import com.zarembin.epampjsp.proxy.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersListDAO {

    private final static String SQL_SELECT_ORDERS =
            "SELECT id_order, user_name, date_of_receiving,is_cash_payment FROM cafedb.orders;";

    private final static String SQL_SELECT_ORDERS_BY_NAME =
            "SELECT id_order, user_name, date_of_receiving, is_cash_payment FROM cafedb.orders WHERE user_name=?;";

    private final static String SQL_SELECT_DISHES_BY_ORDER_ID=
            "SELECT dish_name,number_of_servings FROM cafedb.preparing_dishes WHERE id_order=?;";


    public List<Order> findAllOrders() throws DAOException {

        List<Order> OrdersList = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ORDERS);
            while (resultSet.next()) {
                OrdersList.add(new Order(resultSet.getInt(1), resultSet.getString(2),
                        LocalDateTime.of(resultSet.getDate(3).toLocalDate(),resultSet.getTime(3).toLocalTime())
                        ,"1".equals(resultSet.getString(4))));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            }
        }
        return OrdersList;
    }


    public List<Order> findOrdersByName(String userName) throws DAOException {

        List<Order> ordersByNameList = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement  = connection.prepareStatement(SQL_SELECT_ORDERS_BY_NAME);
            preparedStatement.setString(1, userName);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ordersByNameList.add(new Order(resultSet.getInt(1), resultSet.getString(2),
                        LocalDateTime.of(resultSet.getDate(3).toLocalDate(),resultSet.getTime(3).toLocalTime())
                        ,"1".equals(resultSet.getString(4))));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            }
        }
        return ordersByNameList;
    }

    public Map<String,Integer> findOrdersByOrderId(int orderId) throws DAOException {

        Map<String,Integer>  dishesByOrderIdMap = new HashMap<>();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement  = connection.prepareStatement(SQL_SELECT_DISHES_BY_ORDER_ID);
            preparedStatement.setString(1,String.valueOf(orderId));
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                dishesByOrderIdMap.put(resultSet.getString(1),resultSet.getInt(2));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            }
        }
        return dishesByOrderIdMap;
    }
}
