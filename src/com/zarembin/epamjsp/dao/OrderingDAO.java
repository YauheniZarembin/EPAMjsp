package com.zarembin.epamjsp.dao;

import com.zarembin.epamjsp.entity.Dish;
import com.zarembin.epamjsp.entity.Order;
import com.zarembin.epamjsp.exception.DAOException;
import com.zarembin.epamjsp.pool.ConnectionPool;
import com.zarembin.epamjsp.pool.ProxyConnection;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class OrderingDAO {
    private static final String SQL_UPDATE_USER_AFTER_ORDER =
            "UPDATE cafedb.personal_info SET loyalty_points=?, money=?, number_of_orders=? WHERE user_name=?;";

    private static final String SQL_INSERT_ORDER =
            "INSERT INTO `cafedb`.`orders` (`user_name`, `date_of_receiving`, `is_cash_payment`) VALUES (?, ?, ?);";

    private static final String SQL_SELECT_ORDER_ID =
            "SELECT id_order FROM cafedb.orders WHERE user_name=? AND date_of_receiving=?;";

    private static final String SQL_INSERT_PREPARING_DISHES =
            "INSERT INTO `cafedb`.`preparing_dishes` (`id_order`, `dish_name`, `number_of_servings`) VALUES (?, ?, ?);";

    private static final String CASH_PAYMENT = "1";
    private static final String NOT_CASH_PAYMENT = "0";

    public void makeOrder(BigDecimal money, int loyaltyPoints, int orderAmout, Order order) throws DAOException {


        ProxyConnection connection = null;
        PreparedStatement preparedStatementUpdateUser = null;
        PreparedStatement preparedStatementInsertOrder = null;
        PreparedStatement preparedStatementSelectOrderId;
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
            preparedStatementInsertOrder.setString(3, order.isCashPayment() ? CASH_PAYMENT : NOT_CASH_PAYMENT);

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
                throw new DAOException(e1.getMessage(), e1);
            }
            throw new DAOException(e.getMessage(), e);
        } finally {
            UtilDAO.closeStatement(preparedStatementUpdateUser);
            UtilDAO.closeStatement(preparedStatementInsertOrder);
            UtilDAO.closeStatement(preparedStatementInsertPreparingDishes);
            UtilDAO.setAutoCommitTrueAndCloseConnection(connection);
        }
    }
}