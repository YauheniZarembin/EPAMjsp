package com.zarembin.epampjsp.dao;

import com.zarembin.epampjsp.entity.Dish;
import com.zarembin.epampjsp.entity.TypeOfDish;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.proxy.ConnectionPool;
import com.zarembin.epampjsp.proxy.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {
    private final static  String SQL_SELECT_DISHES =
            "SELECT dish_name , type_of_dish , price , image_path FROM cafedb.menu";

    private final static String SQL_SELECT_DISHES_BY_TYPE =
            "SELECT dish_name , type_of_dish , price , image_path FROM cafedb.menu WHERE type_of_dish=?";

    private final static String SQL_SELECT_DISHES_BY_NAME =
            "SELECT dish_name , type_of_dish , price , image_path FROM cafedb.menu WHERE dish_name=?";


    public List<Dish> findAllDishes() throws DAOException {

        List<Dish> dishList = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_DISHES);
            while (resultSet.next()) {
                dishList.add(new Dish(resultSet.getString(1), TypeOfDish.valueOf(resultSet.getString(2).toUpperCase()),
                        resultSet.getBigDecimal(3), resultSet.getString(4)));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Сonnection close error: " + e);
                }
            }
        }
        return dishList;
    }


    public List<Dish> findDishesByType(TypeOfDish typeOfDish) throws DAOException {

        List<Dish> dishByTypeList = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement  = connection.prepareStatement(SQL_SELECT_DISHES_BY_TYPE);
            preparedStatement.setString(1, typeOfDish.toString());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                dishByTypeList.add(new Dish(resultSet.getString(1), TypeOfDish.valueOf(resultSet.getString(2).toUpperCase()),
                        resultSet.getBigDecimal(3), resultSet.getString(4)));
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
                    System.err.println("Сonnection close error: " + e);
                }
            }
        }
        return dishByTypeList;
    }

    public Dish findDishByName(String dishName) throws DAOException {

        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement  = connection.prepareStatement(SQL_SELECT_DISHES_BY_NAME);
            preparedStatement.setString(1, dishName);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Dish(resultSet.getString(1), TypeOfDish.valueOf(resultSet.getString(2).toUpperCase()),
                        resultSet.getBigDecimal(3), resultSet.getString(4));
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
        return null;
    }
}