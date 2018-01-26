package com.zarembin.epampjsp.dao;

import com.zarembin.epampjsp.entity.Review;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.proxy.ConnectionPool;
import com.zarembin.epampjsp.proxy.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReviewsDAO {

    private final static String SQL_SELECT_REVIEWS =
            "SELECT user_name, mark, text_review FROM cafedb.reviews;";

    private final static String SQL_INSERT_REVIEW =
            "INSERT INTO cafedb.reviews (user_name, mark, text_review) VALUES (?, ?, ?);";

    public List<Review> findReviews() throws DAOException {
        List<Review> reviewsList = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_REVIEWS);
            while (resultSet.next()) {
                reviewsList.add(new Review(resultSet.getString(1), resultSet.getInt(2), resultSet.getString(3)));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            }
        }
        return reviewsList;
    }

    public void insertNewReview(String userName, int mark, String textReview) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_REVIEW);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, String.valueOf(mark));
            preparedStatement.setString(3, textReview);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        } finally {
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
