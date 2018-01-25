package com.zarembin.epampjsp.dao;

import com.zarembin.epampjsp.entity.Order;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.proxy.ConnectionPool;
import com.zarembin.epampjsp.proxy.ProxyConnection;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdersListDAO {

    private final String SQL_SELECT_ORDERS =
            "SELECT id_order, user_name, date_of_receiving,is_cash_payment FROM cafedb.orders;";


    public List<Order> findAllOrders() throws DAOException {

        List<Order> listOrders = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ORDERS);
            while (resultSet.next()) {
                listOrders.add(new Order(resultSet.getInt(1), resultSet.getString(2),
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
                    System.err.println("Сonnection close error: " + e);
                }
            }
        }
        return listOrders;
    }
}
