package com.zarembin.epampjsp.dao;

import com.sun.org.apache.regexp.internal.RE;
import com.zarembin.epampjsp.entity.Dish;
import com.zarembin.epampjsp.entity.Order;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.proxy.ConnectionPool;
import com.zarembin.epampjsp.proxy.ProxyConnection;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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


//    public List<Order> findAllOrders() throws DAOException {
//
//        List<Order> OrdersList = new ArrayList<>();
//        ProxyConnection connection = null;
//        Statement statement;
//        ResultSet resultSet;
//        try {
//            connection = ConnectionPool.getInstance().getConnection();
//            statement = connection.createStatement();
//            resultSet = statement.executeQuery(SQL_SELECT_ORDERS);
//            while (resultSet.next()) {
//                OrdersList.add(new Order(resultSet.getInt(1), resultSet.getString(2),
//                        LocalDateTime.of(resultSet.getDate(3).toLocalDate(),resultSet.getTime(3).toLocalTime())
//                        ,"1".equals(resultSet.getString(4))));
//            }
//        } catch (SQLException e) {
//            throw new DAOException(e.getMessage(), e.getCause());
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    throw new DAOException(e.getMessage(), e.getCause());
//                }
//            }
//        }
//        return OrdersList;
//    }


    public List<Order> findOrdersByName(String userName) throws DAOException {

        List<Order> ordersByUserNameList = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement preparedStatementOrder = null;
        PreparedStatement preparedStatementDishes = null;
        ResultSet resultSetOrder;
        ResultSet resultSetDishes;
        MenuDAO menuDAO = new MenuDAO();
        Map<Dish, Integer> dishOrderMap = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatementOrder  = connection.prepareStatement(SQL_SELECT_ORDERS_BY_NAME);
            preparedStatementDishes = connection.prepareStatement(SQL_SELECT_DISHES_BY_ORDER_ID);
            preparedStatementOrder.setString(1, userName);
            resultSetOrder = preparedStatementOrder.executeQuery();

            while (resultSetOrder.next()) {
                preparedStatementDishes.setString(1, resultSetOrder.getString(1));
                resultSetDishes = preparedStatementDishes.executeQuery();
                BigDecimal orderCost = new BigDecimal(0);
                dishOrderMap = new HashMap<>();
                while (resultSetDishes.next()) {
                    Dish dishFromOrder = menuDAO.findDishByName(resultSetDishes.getString(1));
                    Integer numberOfServings = resultSetDishes.getInt(2);
                    orderCost = orderCost.add(dishFromOrder.getPrice().multiply(new BigDecimal(numberOfServings)));
                    dishOrderMap.put(dishFromOrder, numberOfServings);
                }
                ordersByUserNameList.add(new Order(resultSetOrder.getInt(1), resultSetOrder.getString(2),
                        LocalDateTime.of(resultSetOrder.getDate(3).toLocalDate(), resultSetOrder.getTime(3).toLocalTime()),
                        "1".equals(resultSetOrder.getString(4)),
                        orderCost, dishOrderMap));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (preparedStatementOrder != null) {
                try {
                    preparedStatementOrder.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            }
            if (preparedStatementDishes != null) {
                try {
                    preparedStatementDishes.close();
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
        return ordersByUserNameList;
    }
}
