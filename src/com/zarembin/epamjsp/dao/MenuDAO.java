package com.zarembin.epamjsp.dao;

import com.zarembin.epamjsp.entity.Dish;
import com.zarembin.epamjsp.entity.TypeOfDish;
import com.zarembin.epamjsp.exception.DAOException;
import com.zarembin.epamjsp.pool.ConnectionPool;
import com.zarembin.epamjsp.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {

    private static final String SQL_SELECT_DISHES_BY_TYPE =
            "SELECT dish_name , type_of_dish , price , image_path, is_no_more FROM cafedb.menu WHERE type_of_dish=?";

    private static final String SQL_SELECT_DISHES_BY_NAME =
            "SELECT dish_name , type_of_dish , price , image_path, is_no_more FROM cafedb.menu WHERE dish_name=?";

    private static final String SQL_SELECT_DISHES_IS_NO_MORE =
            "SELECT is_no_more FROM cafedb.menu WHERE dish_name=?";

    private static final String SQL_UPDATE_DISH_PICTURE =
            "UPDATE cafedb.menu SET image_path=? WHERE dish_name=?;";

    private static final String SQL_UPDATE_DISH_PRICE =
            "UPDATE cafedb.menu SET price=? WHERE dish_name=?;";

    private static final String SQL_INSERT_DISH =
            "INSERT INTO cafedb.menu (dish_name, type_of_dish, price) VALUES (?, ?, ?);";

    private static final String SQL_UPDATE_IS_NO_MORE =
            "UPDATE cafedb.menu SET is_no_more=? WHERE dish_name=?;";

    private static final String SQL_DELETE_DISH =
            "DELETE FROM cafedb.menu WHERE dish_name=?;";

    private static final String NO_MORE = "1";


    public void deleteDish(String dishName) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_DISH);
            preparedStatement.setString(1, dishName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            UtilDAO.closeStatement(preparedStatement);
            UtilDAO.closeConnection(connection);
        }
    }

    public List<Dish> findDishesByType(TypeOfDish typeOfDish) throws DAOException {

        List<Dish> dishByTypeList = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_DISHES_BY_TYPE);
            preparedStatement.setString(1, typeOfDish.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                dishByTypeList.add(new Dish(resultSet.getString(1), TypeOfDish.valueOf(resultSet.getString(2).toUpperCase()),
                        resultSet.getBigDecimal(3), resultSet.getString(4), NO_MORE.equals(resultSet.getString(5))));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            UtilDAO.closeStatement(preparedStatement);
            UtilDAO.closeConnection(connection);
        }
        return dishByTypeList;
    }

    public Dish findDishByName(String dishName) throws DAOException {

        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_DISHES_BY_NAME);
            preparedStatement.setString(1, dishName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Dish(resultSet.getString(1), TypeOfDish.valueOf(resultSet.getString(2).toUpperCase()),
                        resultSet.getBigDecimal(3), resultSet.getString(4), NO_MORE.equals(resultSet.getString(5)));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            UtilDAO.closeStatement(preparedStatement);
            UtilDAO.closeConnection(connection);
        }
        return null;
    }

    public void changeDishPicture(String dishName, String pathPicture) throws DAOException {
        UtilDAO.changeParameter(dishName, pathPicture , SQL_UPDATE_DISH_PICTURE);
    }

    public void changeDishPrice(String dishName, String newPrice) throws DAOException {
        UtilDAO.changeParameter(dishName,newPrice,SQL_UPDATE_DISH_PRICE);
    }

    public void changeIsNoMore(String dishName) throws DAOException {
        UtilDAO.changeParameter(dishName, NO_MORE, SQL_UPDATE_IS_NO_MORE);
    }

    public void insertNewDish(String dishName, TypeOfDish typeOfDish, String price) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatementInsertUser = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatementInsertUser = connection.prepareStatement(SQL_INSERT_DISH);
            preparedStatementInsertUser.setString(1, dishName);
            preparedStatementInsertUser.setString(2, typeOfDish.toString());
            preparedStatementInsertUser.setString(3, price);
            preparedStatementInsertUser.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            UtilDAO.closeStatement(preparedStatementInsertUser);
            UtilDAO.closeConnection(connection);
        }
    }

    public boolean isDishNoMore(String dishName) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_DISHES_IS_NO_MORE);
            preparedStatement.setString(1, dishName);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? NO_MORE.equals(resultSet.getString(1)) : false;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            UtilDAO.closeStatement(preparedStatement);
            UtilDAO.closeConnection(connection);
        }
    }

}
