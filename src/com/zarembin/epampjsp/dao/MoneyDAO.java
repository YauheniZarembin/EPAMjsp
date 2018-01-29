package com.zarembin.epampjsp.dao;

import com.zarembin.epampjsp.entity.User;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.proxy.ConnectionPool;
import com.zarembin.epampjsp.proxy.ProxyConnection;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MoneyDAO {

    private final static String SQL_SELECT_MONEY_FROM_CARD =
            "SELECT amount FROM cafedb.bank_info WHERE card_number=? AND `password` = ?;";

    private final static String SQL_UPDATE_USER_MONEY =
            "UPDATE cafedb.personal_info SET money=? WHERE user_name=?;";

    private final static String SQL_UPDATE_BANK_MONEY =
            "UPDATE cafedb.bank_info SET amount=? WHERE card_number=?;";


    public BigDecimal findMoneyFromCard(String loginСard, String passwordCard) throws DAOException {
        BigDecimal money = null;
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_MONEY_FROM_CARD);
            preparedStatement.setString(1, loginСard);
            preparedStatement.setString(2, passwordCard);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                money = resultSet.getBigDecimal(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (preparedStatement != null){
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
        return money;
    }

    public void topUpUserMoney(BigDecimal moneyBank , BigDecimal moneyUser , User user) throws DAOException {
        BigDecimal money = null;
        ProxyConnection connection = null;
        PreparedStatement preparedStatementUpdateMoneyBank = null;
        PreparedStatement preparedStatementUpdateMoneyUser = null;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            preparedStatementUpdateMoneyBank = connection.prepareStatement(SQL_UPDATE_BANK_MONEY);
            preparedStatementUpdateMoneyUser = connection.prepareStatement(SQL_UPDATE_USER_MONEY);
            preparedStatementUpdateMoneyBank.setString(1, moneyBank.toString());
            preparedStatementUpdateMoneyBank.setString(2, user.getCardNumber());
            preparedStatementUpdateMoneyUser.setString(1, moneyUser.toString());
            preparedStatementUpdateMoneyUser.setString(2, user.getUserName());
            preparedStatementUpdateMoneyBank.executeUpdate();
            preparedStatementUpdateMoneyUser.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e.getMessage(), e.getCause());
            }
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (preparedStatementUpdateMoneyBank != null){
                try {
                    preparedStatementUpdateMoneyBank.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            }
            if (preparedStatementUpdateMoneyUser != null){
                try {
                    preparedStatementUpdateMoneyUser.close();
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