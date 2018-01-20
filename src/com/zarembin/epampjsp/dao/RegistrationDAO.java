package com.zarembin.epampjsp.dao;

import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.proxy.ConnectionPool;
import com.zarembin.epampjsp.proxy.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationDAO {

    private final String SQL_CHECK_CARD_NUMBER =
            "SELECT card_number FROM cafedb.bank_info where card_number=?";

    private final String SQL_CHECK_USER_NAME =
            "SELECT user_name FROM cafedb.personal_info where user_name=?";

    private final String SQL_INSERT_USER =
            "INSERT INTO `cafedb`.`personal_info` (user_name, password, is_admin, is_ban, `name`, last_name, loyalty_points, money, `e-mail`,number_of_orders, card_number) VALUES (?, ?, 0, 0, ?, ?, 0, 0, ?, 0, ?)";

    public boolean insertNewUser(String userName, String password, String name, String lastname, String email, String cardNumber) throws DAOException {
        System.out.println("DA0DA0DAO");
        ProxyConnection connection = null;
        PreparedStatement preparedStatementInsertUser;
        PreparedStatement preparedStatementCheckCardNumber;
        PreparedStatement preparedStatementCheckUserName;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatementInsertUser = connection.prepareStatement(SQL_INSERT_USER);
            preparedStatementInsertUser.setString(1, userName);
            preparedStatementInsertUser.setString(2, password);
            preparedStatementInsertUser.setString(3, name);
            preparedStatementInsertUser.setString(4, lastname);
            preparedStatementInsertUser.setString(5, email);
            preparedStatementInsertUser.setString(6, cardNumber);


            preparedStatementCheckCardNumber = connection.prepareStatement(SQL_CHECK_CARD_NUMBER);
            preparedStatementCheckCardNumber.setString(1, cardNumber);

            preparedStatementCheckUserName = connection.prepareStatement(SQL_CHECK_USER_NAME);
            preparedStatementCheckUserName.setString(1, userName);

            ////////   check e-mail

            resultSet = preparedStatementCheckUserName.executeQuery();
            if (!resultSet.next()) {
                resultSet = preparedStatementCheckCardNumber.executeQuery();
                if (resultSet.next()) {
                    preparedStatementInsertUser.executeUpdate();
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(),e.getCause());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Сonnection close error: " + e);
                }
            }
        }
        return false;
    }
}
