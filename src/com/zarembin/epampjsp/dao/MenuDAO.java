package com.zarembin.epampjsp.dao;

import com.zarembin.epampjsp.entity.Dish;
import com.zarembin.epampjsp.entity.TypeOfDish;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.proxy.ConnectionPool;
import com.zarembin.epampjsp.proxy.ProxyConnection;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {
    private final static String SQL_SELECT_DISHES =
            "SELECT dish_name , type_of_dish , price , image_path, is_no_more FROM cafedb.menu";

    private final static String SQL_SELECT_DISHES_BY_TYPE =
            "SELECT dish_name , type_of_dish , price , image_path, is_no_more FROM cafedb.menu WHERE type_of_dish=?";

    private final static String SQL_SELECT_DISHES_BY_NAME =
            "SELECT dish_name , type_of_dish , price , image_path, is_no_more FROM cafedb.menu WHERE dish_name=?";

    private final static String SQL_SELECT_DISHES_IS_NO_MORE =
            "SELECT is_no_more FROM cafedb.menu WHERE dish_name=?";

    private final static String SQL_UPDATE_DISH_PICTURE =
            "UPDATE cafedb.menu SET image_path=? WHERE dish_name=?;";

    private final static String SQL_UPDATE_DISH_PRICE =
            "UPDATE cafedb.menu SET price=? WHERE dish_name=?;";

    private final static String SQL_INSERT_DISH =
            "INSERT INTO cafedb.menu (dish_name, type_of_dish, price) VALUES (?, ?, ?);";

    private final static String SQL_UPDATE_IS_NO_MORE =
            "UPDATE cafedb.menu SET is_no_more='1' WHERE dish_name=?;";

    private final static String SQL_DELETE_DISH =
            "DELETE FROM cafedb.menu WHERE dish_name=?;";


    public void deleteDish(String dishName) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_DISH);
            preparedStatement.setString(1, dishName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (preparedStatement != null) {
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
    }


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
                        resultSet.getBigDecimal(3), resultSet.getString(4), "1".equals(resultSet.getString(5))));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (statement != null) {
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
            preparedStatement = connection.prepareStatement(SQL_SELECT_DISHES_BY_TYPE);
            preparedStatement.setString(1, typeOfDish.toString());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                dishByTypeList.add(new Dish(resultSet.getString(1), TypeOfDish.valueOf(resultSet.getString(2).toUpperCase()),
                        resultSet.getBigDecimal(3), resultSet.getString(4), "1".equals(resultSet.getString(5))));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (preparedStatement != null) {
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
            preparedStatement = connection.prepareStatement(SQL_SELECT_DISHES_BY_NAME);
            preparedStatement.setString(1, dishName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Dish(resultSet.getString(1), TypeOfDish.valueOf(resultSet.getString(2).toUpperCase()),
                        resultSet.getBigDecimal(3), resultSet.getString(4), "1".equals(resultSet.getString(5)));
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (preparedStatement != null) {
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

    public void changeDishPicture(String dishName, String pathPicture) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_DISH_PICTURE);
            preparedStatement.setString(1, pathPicture);
            preparedStatement.setString(2, dishName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (preparedStatement != null) {
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
    }

    public void changeDishPrice(String dishName, String newPrice) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_DISH_PRICE);
            preparedStatement.setString(1, newPrice);
            preparedStatement.setString(2, dishName);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (preparedStatement != null) {
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
    }

    public void changeIsNoMore(String dishName) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_IS_NO_MORE);
            preparedStatement.setString(1, dishName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (preparedStatement != null) {
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
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (preparedStatementInsertUser != null) {
                try {
                    preparedStatementInsertUser.close();
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
            return resultSet.next() ? "1".equals(resultSet.getString(1)) : false;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (preparedStatement != null) {
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
    }
}
