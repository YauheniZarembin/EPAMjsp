package com.zarembin.epampjsp.dao;

import com.sun.org.apache.regexp.internal.RE;
import com.zarembin.epampjsp.entity.Dish;
import com.zarembin.epampjsp.entity.Order;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.proxy.ConnectionPool;
import com.zarembin.epampjsp.proxy.ProxyConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersListDAO {

    private final static String SQL_SELECT_ORDERS =
            "SELECT id_order, user_name, date_of_receiving,is_cash_payment FROM cafedb.orders;";

    private final static String SQL_SELECT_ORDERS_BY_NAME =
            "SELECT id_order, user_name, date_of_receiving, is_cash_payment FROM cafedb.orders WHERE user_name=?;";

    private final static String SQL_SELECT_ORDERS_BY_ID =
            "SELECT id_order, user_name, date_of_receiving, is_cash_payment FROM cafedb.orders WHERE id_order=?;";

    private final static String SQL_SELECT_DISHES_BY_ORDER_ID =
            "SELECT dish_name,number_of_servings FROM cafedb.preparing_dishes WHERE id_order=?;";

    private final static String SQL_SELECT_ORDERS_BY_DISH_NAME =
            "SELECT id_order FROM cafedb.preparing_dishes WHERE dish_name=?;";

    private final static String SQL_DELETE_ORDER =  "DELETE FROM cafedb.orders WHERE id_order=?;";

    private final static String SQL_DELETE_PREPARING_DISHES = "DELETE FROM cafedb.preparing_dishes WHERE id_order=?;";


    public void deleteOrder(String orderId) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatementDeleteOrder = null;
        PreparedStatement preparedStatementDeletePreparingDishes = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            preparedStatementDeletePreparingDishes = connection.prepareStatement(SQL_DELETE_PREPARING_DISHES);
            preparedStatementDeleteOrder = connection.prepareStatement(SQL_DELETE_ORDER);
            preparedStatementDeletePreparingDishes.setString(1,orderId);
            preparedStatementDeleteOrder.setString(1,orderId);
            preparedStatementDeletePreparingDishes.executeUpdate();
            preparedStatementDeleteOrder.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e.getMessage(), e.getCause());
            }
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (preparedStatementDeletePreparingDishes != null){
                try {
                    preparedStatementDeletePreparingDishes.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            }
            if (preparedStatementDeleteOrder != null){
                try {
                    preparedStatementDeleteOrder.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            }
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            }
        }
    }

    public Order findOrderById(String orderId) throws DAOException {
        Order order = null;
        ProxyConnection connection = null;
        PreparedStatement preparedStatementOrder = null;
        PreparedStatement preparedStatementDishes = null;
        ResultSet resultSetOrder;
        ResultSet resultSetDishes;
        MenuDAO menuDAO = new MenuDAO();
        Map<Dish, Integer> dishOrderMap = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatementOrder = connection.prepareStatement(SQL_SELECT_ORDERS_BY_ID);
            preparedStatementDishes = connection.prepareStatement(SQL_SELECT_DISHES_BY_ORDER_ID);
            preparedStatementOrder.setString(1, orderId);
            resultSetOrder = preparedStatementOrder.executeQuery();

            if (resultSetOrder.next()) {
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
                order = new Order(resultSetOrder.getInt(1), resultSetOrder.getString(2),
                        LocalDateTime.of(resultSetOrder.getDate(3).toLocalDate(), resultSetOrder.getTime(3).toLocalTime()),
                        "1".equals(resultSetOrder.getString(4)),
                        orderCost, dishOrderMap);
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
        return order;
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

    public boolean isDishInOrders(String dishName) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSetOrder;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_ORDERS_BY_DISH_NAME);
            preparedStatement.setString(1, dishName);
            resultSetOrder = preparedStatement.executeQuery();
            return resultSetOrder.next();
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
                        orderCost, dishOrderMap));
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

    public List<Order> findTodayOrders() throws DAOException {
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
                if (resultSetOrder.getDate(3).toLocalDate().equals(LocalDate.now())) {
                    orderList.add(new Order(resultSetOrder.getInt(1), resultSetOrder.getString(2),
                            LocalDateTime.of(resultSetOrder.getDate(3).toLocalDate(), resultSetOrder.getTime(3).toLocalTime()),
                            "1".equals(resultSetOrder.getString(4)),
                            orderCost, dishOrderMap));
                }
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

    public List<Order> findPastOrders() throws DAOException {
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
                if ((LocalDateTime.now().until(LocalDateTime.of(resultSetOrder.getDate(3).toLocalDate(), resultSetOrder.getTime(3).toLocalTime()), ChronoUnit.HOURS)) <= -3) {
                    orderList.add(new Order(resultSetOrder.getInt(1), resultSetOrder.getString(2),
                            LocalDateTime.of(resultSetOrder.getDate(3).toLocalDate(), resultSetOrder.getTime(3).toLocalTime()),
                            "1".equals(resultSetOrder.getString(4)),
                            orderCost, dishOrderMap));
                }
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

    public List<Order> findFutureOrders() throws DAOException {
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
                if (LocalDate.now().until(resultSetOrder.getDate(3).toLocalDate(), ChronoUnit.DAYS) > 0) {
                    orderList.add(new Order(resultSetOrder.getInt(1), resultSetOrder.getString(2),
                            LocalDateTime.of(resultSetOrder.getDate(3).toLocalDate(), resultSetOrder.getTime(3).toLocalTime()),
                            "1".equals(resultSetOrder.getString(4)),
                            orderCost, dishOrderMap));
                }
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

