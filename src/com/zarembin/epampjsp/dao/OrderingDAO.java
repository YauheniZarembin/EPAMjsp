package com.zarembin.epampjsp.dao;

import com.zarembin.epampjsp.entity.Dish;
import com.zarembin.epampjsp.entity.Order;
import com.zarembin.epampjsp.entity.User;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.proxy.ConnectionPool;
import com.zarembin.epampjsp.proxy.ProxyConnection;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderingDAO {
    private final static String SQL_UPDATE_USER_AFTER_ORDER =
            "UPDATE cafedb.personal_info SET loyalty_points=?, money=?, number_of_orders=? WHERE user_name=?;";

    private final static String SQL_INSERT_ORDER =
            "INSERT INTO `cafedb`.`orders` (`user_name`, `date_of_receiving`, `is_cash_payment`) VALUES (?, ?, ?);";

    private final static String SQL_SELECT_ORDER_ID =
            "SELECT id_order FROM cafedb.orders WHERE user_name=? AND date_of_receiving=?;";

    private final static String SQL_INSERT_PREPARING_DISHES =
            "INSERT INTO `cafedb`.`preparing_dishes` (`id_order`, `dish_name`, `number_of_servings`) VALUES (?, ?, ?);";

    public void makeOrder(BigDecimal money, int loyaltyPoints, int orderAmout, Order order) throws DAOException {


        ProxyConnection connection = null;
        PreparedStatement preparedStatementUpdateUser = null;
        PreparedStatement preparedStatementInsertOrder = null;
        PreparedStatement preparedStatementSelectOrderId = null;
        PreparedStatement preparedStatementInsertPreparingDishes = null;
        try {

            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            preparedStatementUpdateUser = connection.prepareStatement(SQL_UPDATE_USER_AFTER_ORDER);
            preparedStatementUpdateUser.setString(1, String.valueOf(loyaltyPoints));
            preparedStatementUpdateUser.setString(2, money.toString());
            preparedStatementUpdateUser.setString(3, String.valueOf(orderAmout));
            preparedStatementUpdateUser.setString(4, order.getUserName());
            preparedStatementUpdateUser.executeUpdate();

            preparedStatementInsertOrder = connection.prepareStatement(SQL_INSERT_ORDER);
            preparedStatementInsertOrder.setString(1, order.getUserName());
            preparedStatementInsertOrder.setString(2, order.getDateOfReceiving().toString());
            if (order.isCashPayment()) {
                preparedStatementInsertOrder.setString(3, "1");
            } else {
                preparedStatementInsertOrder.setString(3, "0");
            }
            preparedStatementInsertOrder.executeUpdate();

            preparedStatementSelectOrderId = connection.prepareStatement(SQL_SELECT_ORDER_ID);
            preparedStatementSelectOrderId.setString(1,order.getUserName());
            preparedStatementSelectOrderId.setString(2,order.getDateOfReceiving().toString());
            ResultSet orderIdResultSet = preparedStatementSelectOrderId.executeQuery();
            if (orderIdResultSet.last()){
                order.setOrderId(orderIdResultSet.getInt(1));
            }

            preparedStatementInsertPreparingDishes = connection.prepareStatement(SQL_INSERT_PREPARING_DISHES);
            for (Map.Entry<Dish, Integer> entry : order.getDishes().entrySet()) {
                preparedStatementInsertPreparingDishes.setString(1, String.valueOf(order.getOrderId()));
                preparedStatementInsertPreparingDishes.setString(2, entry.getKey().getDishName());
                preparedStatementInsertPreparingDishes.setString(3, entry.getValue().toString());
                preparedStatementInsertPreparingDishes.executeUpdate();

            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e.getMessage(), e.getCause());
            }
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (preparedStatementUpdateUser != null) {
                try {
                    preparedStatementUpdateUser.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            }
            if (preparedStatementInsertOrder != null) {
                try {
                    preparedStatementInsertOrder.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            }
            if (preparedStatementInsertPreparingDishes != null) {
                try {
                    preparedStatementInsertPreparingDishes.close();
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
}