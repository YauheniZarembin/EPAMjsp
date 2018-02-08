package com.zarembin.epamjsp.dao;

import com.zarembin.epamjsp.entity.Dish;
import com.zarembin.epamjsp.entity.Order;
import com.zarembin.epamjsp.exception.DAOException;
import com.zarembin.epamjsp.pool.ConnectionPool;
import com.zarembin.epamjsp.pool.ProxyConnection;

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

    private static final String SQL_SELECT_ORDERS =
            "SELECT id_order, user_name, date_of_receiving,is_cash_payment FROM cafedb.orders;";

    private static final String SQL_SELECT_ORDERS_BY_NAME =
            "SELECT id_order, user_name, date_of_receiving, is_cash_payment FROM cafedb.orders WHERE user_name=?;";

    private static final String SQL_SELECT_ORDERS_BY_ID =
            "SELECT id_order, user_name, date_of_receiving, is_cash_payment FROM cafedb.orders WHERE id_order=?;";

    private static final String SQL_SELECT_DISHES_BY_ORDER_ID =
            "SELECT dish_name,number_of_servings FROM cafedb.preparing_dishes WHERE id_order=?;";

    private static final String SQL_SELECT_ORDERS_BY_DISH_NAME =
            "SELECT id_order FROM cafedb.preparing_dishes WHERE dish_name=?;";

    private static final String SQL_DELETE_ORDER = "DELETE FROM cafedb.orders WHERE id_order=?;";

    private static final String SQL_DELETE_PREPARING_DISHES = "DELETE FROM cafedb.preparing_dishes WHERE id_order=?;";

    private static final String CASH_PAYMENT = "1";
    private static final int DELAY = -3;
    private static final int TODAY = 0;


    public void deleteOrder(String orderId) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatementDeleteOrder = null;
        PreparedStatement preparedStatementDeletePreparingDishes = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            preparedStatementDeletePreparingDishes = connection.prepareStatement(SQL_DELETE_PREPARING_DISHES);
            preparedStatementDeleteOrder = connection.prepareStatement(SQL_DELETE_ORDER);
            preparedStatementDeletePreparingDishes.setString(1, orderId);
            preparedStatementDeleteOrder.setString(1, orderId);
            preparedStatementDeletePreparingDishes.executeUpdate();
            preparedStatementDeleteOrder.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e1.getMessage(), e1);
            }
            throw new DAOException(e.getMessage(), e);
        } finally {
            UtilDAO.closeStatement(preparedStatementDeletePreparingDishes);
            UtilDAO.closeStatement(preparedStatementDeleteOrder);
            UtilDAO.setAutoCommitTrueAndCloseConnection(connection);
        }
    }

    public Order findOrderById(String orderId) throws DAOException {
        Order order = null;
        ProxyConnection connection = null;
        PreparedStatement preparedStatementOrder = null;
        PreparedStatement preparedStatementDishes = null;
        ResultSet resultSetOrder;
        ResultSet resultSetDishes;
        Map<Dish, Integer> dishOrderMap;
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
                orderCost = formOrderCostAndOrderDishesMap(resultSetDishes, dishOrderMap, orderCost);
                order = new Order(resultSetOrder.getInt(1), resultSetOrder.getString(2),
                        LocalDateTime.of(resultSetOrder.getDate(3).toLocalDate(), resultSetOrder.getTime(3).toLocalTime()),
                        CASH_PAYMENT.equals(resultSetOrder.getString(4)),
                        orderCost, dishOrderMap);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            UtilDAO.closeStatement(preparedStatementOrder);
            UtilDAO.closeStatement(preparedStatementDishes);
            UtilDAO.closeConnection(connection);
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
        Map<Dish, Integer> dishOrderMap;
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
                orderCost = formOrderCostAndOrderDishesMap(resultSetDishes, dishOrderMap, orderCost);
                ordersByUserNameList.add(new Order(resultSetOrder.getInt(1), resultSetOrder.getString(2),
                        LocalDateTime.of(resultSetOrder.getDate(3).toLocalDate(), resultSetOrder.getTime(3).toLocalTime()),
                        CASH_PAYMENT.equals(resultSetOrder.getString(4)),
                        orderCost, dishOrderMap));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            UtilDAO.closeStatement(preparedStatementOrder);
            UtilDAO.closeStatement(preparedStatementDishes);
            UtilDAO.closeConnection(connection);
        }
        return ordersByUserNameList;
    }

    public boolean isDishInOrders(String dishName) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_ORDERS_BY_DISH_NAME);
            preparedStatement.setString(1, dishName);
            ResultSet resultSetOrder = preparedStatement.executeQuery();
            return resultSetOrder.next();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            UtilDAO.closeStatement(preparedStatement);
            UtilDAO.closeConnection(connection);
        }
    }


    public List<Order> findTodayOrders() throws DAOException {
        List<Order> orderList = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statementOrder = null;
        PreparedStatement preparedStatementDishes = null;
        ResultSet resultSetOrder;
        ResultSet resultSetDishes;
        Map<Dish, Integer> dishOrderMap;
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
                orderCost = formOrderCostAndOrderDishesMap(resultSetDishes, dishOrderMap, orderCost);
                if (resultSetOrder.getDate(3).toLocalDate().equals(LocalDate.now())) {
                    orderList.add(new Order(resultSetOrder.getInt(1), resultSetOrder.getString(2),
                            LocalDateTime.of(resultSetOrder.getDate(3).toLocalDate(), resultSetOrder.getTime(3).toLocalTime()),
                            CASH_PAYMENT.equals(resultSetOrder.getString(4)),
                            orderCost, dishOrderMap));
                }
            }
            return orderList;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            UtilDAO.closeStatement(statementOrder);
            UtilDAO.closeStatement(preparedStatementDishes);
            UtilDAO.closeConnection(connection);
        }
    }

    public List<Order> findPastOrders() throws DAOException {
        List<Order> orderList = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statementOrder = null;
        PreparedStatement preparedStatementDishes = null;
        ResultSet resultSetOrder;
        ResultSet resultSetDishes;
        Map<Dish, Integer> dishOrderMap;
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
                orderCost = formOrderCostAndOrderDishesMap(resultSetDishes, dishOrderMap, orderCost);
                if ((LocalDateTime.now().until(LocalDateTime.of(resultSetOrder.getDate(3).toLocalDate(), resultSetOrder.getTime(3).toLocalTime()), ChronoUnit.HOURS)) <= DELAY) {
                    orderList.add(new Order(resultSetOrder.getInt(1), resultSetOrder.getString(2),
                            LocalDateTime.of(resultSetOrder.getDate(3).toLocalDate(), resultSetOrder.getTime(3).toLocalTime()),
                            CASH_PAYMENT.equals(resultSetOrder.getString(4)),
                            orderCost, dishOrderMap));
                }
            }
            return orderList;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            UtilDAO.closeStatement(statementOrder);
            UtilDAO.closeStatement(preparedStatementDishes);
            UtilDAO.closeConnection(connection);
        }
    }

    public List<Order> findFutureOrders() throws DAOException {
        List<Order> orderList = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statementOrder = null;
        PreparedStatement preparedStatementDishes = null;
        ResultSet resultSetOrder;
        ResultSet resultSetDishes;
        Map<Dish, Integer> dishOrderMap;
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
                orderCost = formOrderCostAndOrderDishesMap(resultSetDishes, dishOrderMap, orderCost);
                if (LocalDate.now().until(resultSetOrder.getDate(3).toLocalDate(), ChronoUnit.DAYS) > TODAY) {
                    orderList.add(new Order(resultSetOrder.getInt(1), resultSetOrder.getString(2),
                            LocalDateTime.of(resultSetOrder.getDate(3).toLocalDate(), resultSetOrder.getTime(3).toLocalTime()),
                            CASH_PAYMENT.equals(resultSetOrder.getString(4)),
                            orderCost, dishOrderMap));
                }
            }
            return orderList;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            UtilDAO.closeStatement(statementOrder);
            UtilDAO.closeStatement(preparedStatementDishes);
            UtilDAO.closeConnection(connection);
        }
    }

    private BigDecimal formOrderCostAndOrderDishesMap(ResultSet resultSet, Map<Dish, Integer> dishOrderMap, BigDecimal orderCost) throws SQLException, DAOException {
        while (resultSet.next()) {
            Dish dishFromOrder = new MenuDAO().findDishByName(resultSet.getString(1));
            Integer numberOfServings = resultSet.getInt(2);
            orderCost = orderCost.add(dishFromOrder.getPrice().multiply(new BigDecimal(numberOfServings)));
            dishOrderMap.put(dishFromOrder, numberOfServings);
        }
        return orderCost;
    }
}

