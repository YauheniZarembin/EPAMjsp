package com.zarembin.epamjsp.dao;

import com.zarembin.epamjsp.exception.DAOException;
import com.zarembin.epamjsp.pool.ConnectionPool;
import com.zarembin.epamjsp.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UtilDAO {

    static void closeStatement(Statement statement) throws DAOException {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DAOException(e.getMessage(), e);
            }
        }
    }

    static void closeConnection(ProxyConnection connection) throws DAOException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException(e.getMessage(), e);
            }
        }
    }

    static void setAutoCommitTrueAndCloseConnection(ProxyConnection connection) throws DAOException {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                throw new DAOException(e.getMessage(), e);
            }
        }
    }

    static void changeParameter(String Name, String parameter, String sqlRequest) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(sqlRequest);
            preparedStatement.setString(1, parameter);
            preparedStatement.setString(2, Name);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }
}