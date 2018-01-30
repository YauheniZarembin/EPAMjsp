package com.zarembin.epampjsp.dao;

import com.sun.org.apache.regexp.internal.RE;
import com.zarembin.epampjsp.entity.Dish;
import com.zarembin.epampjsp.entity.Order;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.proxy.ConnectionPool;
import com.zarembin.epampjsp.proxy.ProxyConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersListDAO {

    private final static String SQL_SELECT_ORDERS =
            "SELECT id_order, user_name, date_of_receiving,is_cash_payment,is_received FROM cafedb.orders;";

    private final static String SQL_SELECT_ORDERS_BY_NAME =
            "SELECT id_order, user_name, date_of_receiving, is_cash_payment, is_received FROM cafedb.orders WHERE user_name=?;";

    private final static String SQL_SELECT_DISHES_BY_ORDER_ID =
            "SELECT dish_name,number_of_servings FROM cafedb.preparing_dishes WHERE id_order=?;";

    private final static String SQL_UPDATE_ORDER_RECEIVED =
            "UPDATE cafedb.orders SET is_received='1' WHERE id_order=?;";


    public void changeOrderReceived(String orderId) throws DAOException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement;

        try {
            System.out.println(orderId + "DAo");
            preparedStatement = connection.prepareStatement(SQL_UPDATE_ORDER_RECEIVED);
            preparedStatement.setString(1,orderId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
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
            preparedStatementOrder = connection.prepareStatement(SQL_SELECT_ORDERS_BY_NAME);
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
                        orderCost, dishOrderMap, "1".equals(resultSetOrder.getString(5))));
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

    public List<Order> findAllOrders() throws DAOException {
        List<Order> orderList = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statementOrder = null;
        PreparedStatement preparedStatementDishes = null;
        ResultSet resultSetOrder;
        ResultSet resultSetDishes;
        MenuDAO menuDAO = new MenuDAO();
        Map<Dish, Integer> dishOrderMap = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statementOrder = connection.createStatement();
            preparedStatementDishes = connection.prepareStatement(SQL_SELECT_DISHES_BY_ORDER_ID);
            resultSetOrder = statementOrder.executeQuery(SQL_SELECT_ORDERS);
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
                orderList.add(new Order(resultSetOrder.getInt(1), resultSetOrder.getString(2),
                        LocalDateTime.of(resultSetOrder.getDate(3).toLocalDate(), resultSetOrder.getTime(3).toLocalTime()),
                        "1".equals(resultSetOrder.getString(4)),
                        orderCost, dishOrderMap, "1".equals(resultSetOrder.getString(5))));
            }
            return orderList;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (statementOrder != null) {
                try {
                    statementOrder.close();
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
    }
}
//    public Map<Dish,Integer> findDishesByOrderID(int orderId) throws DAOException {
//        ProxyConnection connection = null;
//        PreparedStatement preparedStatementDishes = null;
//        ResultSet resultSetDishes;
//        MenuDAO menuDAO = new MenuDAO();
//        Map<Dish, Integer> dishOrderMap = null;
//        try {
//            connection = ConnectionPool.getInstance().getConnection();
//            preparedStatementDishes = connection.prepareStatement(SQL_SELECT_DISHES_BY_ORDER_ID);
//            preparedStatementDishes.setString(1,String.valueOf(orderId));
//            resultSetDishes = preparedStatementDishes.executeQuery();
//
//            while (resultSetDishes.next()) {
//                Dish dish = menuDAO.findDishByName(resultSetDishes.getString(1));
//                Integer numberOfServings = resultSetDishes.getInt(2);
//                orderCost = orderCost.add(dishFromOrder.getPrice().multiply(new BigDecimal(numberOfServings)));
//                dishOrderMap.put(dishFromOrder, numberOfServings);
////                preparedStatementDishes.setString(1, resultSetOrder.getString(1));
////                resultSetDishes = preparedStatementDishes.executeQuery();
////                BigDecimal orderCost = new BigDecimal(0);
////                dishOrderMap = new HashMap<>();
////                while (resultSetDishes.next()) {
////                    Dish dishFromOrder = menuDAO.findDishByName(resultSetDishes.getString(1));
////                    Integer numberOfServings = resultSetDishes.getInt(2);
////                    orderCost = orderCost.add(dishFromOrder.getPrice().multiply(new BigDecimal(numberOfServings)));
////                    dishOrderMap.put(dishFromOrder, numberOfServings);
//
//            }
//        } catch (SQLException e) {
//            throw new DAOException(e.getMessage(), e.getCause());
//        } finally {
//            if (preparedStatementDishes != null) {
//                try {
//                    preparedStatementDishes.close();
//                } catch (SQLException e) {
//                    throw new DAOException(e.getMessage(), e.getCause());
//                }
//            }
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    throw new DAOException(e.getMessage(), e.getCause());
//                }
//            }
//        }
//        return dishOrderMap;
//    }

