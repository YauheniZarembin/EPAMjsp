package com.zarembin.epamjsp.dao;

import com.zarembin.epamjsp.exception.DAOException;
import com.zarembin.epamjsp.pool.ConnectionPool;
import com.zarembin.epamjsp.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationDAO {

    private static final String SQL_CHECK_CARD_NUMBER =
            "SELECT card_number FROM cafedb.bank_info where card_number=? AND password=?;";

    private static final String SQL_CHECK_USER_NAME =
            "SELECT user_name FROM cafedb.personal_info where user_name=?";

    private static final String SQL_INSERT_USER =
            "INSERT INTO `cafedb`.`personal_info` (user_name, password, is_admin, is_ban, `name`, last_name, loyalty_points, money, `e-mail`,number_of_orders, card_number) VALUES (?, ?, 0, 0, ?, ?, 0, 0, ?, 0, ?)";


    public boolean isSuchUserExist(String userName) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatementCheckUserName = null;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatementCheckUserName = connection.prepareStatement(SQL_CHECK_USER_NAME);
            preparedStatementCheckUserName.setString(1, userName);
            resultSet = preparedStatementCheckUserName.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            UtilDAO.closeStatement(preparedStatementCheckUserName);
            UtilDAO.closeConnection(connection);
        }
    }

    public boolean isSuchCardExist(String cardNumber, String cardPassword) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatementCheckCard = null;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatementCheckCard = connection.prepareStatement(SQL_CHECK_CARD_NUMBER);
            preparedStatementCheckCard.setString(1, cardNumber);
            preparedStatementCheckCard.setString(2, cardPassword);
            resultSet = preparedStatementCheckCard.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            UtilDAO.closeStatement(preparedStatementCheckCard);
            UtilDAO.closeConnection(connection);
        }
    }

    public void insertNewUser(String userName, String password, String name, String lastname, String email, String cardNumber) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatementInsertUser = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatementInsertUser = connection.prepareStatement(SQL_INSERT_USER);
            preparedStatementInsertUser.setString(1, userName);
            preparedStatementInsertUser.setString(2, password);
            preparedStatementInsertUser.setString(3, name);
            preparedStatementInsertUser.setString(4, lastname);
            preparedStatementInsertUser.setString(5, email);
            preparedStatementInsertUser.setString(6, cardNumber);

            preparedStatementInsertUser.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            UtilDAO.closeStatement(preparedStatementInsertUser);
            UtilDAO.closeConnection(connection);
        }
    }
}