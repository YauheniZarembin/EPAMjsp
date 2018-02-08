package com.zarembin.epamjsp.dao;

import com.zarembin.epamjsp.entity.Review;
import com.zarembin.epamjsp.exception.DAOException;
import com.zarembin.epamjsp.pool.ConnectionPool;
import com.zarembin.epamjsp.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReviewsDAO {

    private static final String SQL_SELECT_REVIEWS =
            "SELECT id_review, user_name, mark, text_review FROM cafedb.reviews;";

    private static final String SQL_INSERT_REVIEW =
            "INSERT INTO cafedb.reviews (user_name, mark, text_review) VALUES (?, ?, ?);";

    private static final String SQL_DELETE_REVIEW =
            "DELETE FROM cafedb.reviews WHERE id_review=?;";


    public List<Review> findReviews() throws DAOException {
        List<Review> reviewsList = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_REVIEWS);
            while (resultSet.next()) {
                reviewsList.add(new Review(resultSet.getInt(1),resultSet.getString(2),
                        resultSet.getInt(3), resultSet.getString(4)));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            UtilDAO.closeStatement(statement);
            UtilDAO.closeConnection(connection);
        }
        return reviewsList;
    }

    public void insertNewReview(String userName, int mark, String textReview) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_REVIEW);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, Integer.toString(mark));
            preparedStatement.setString(3, textReview);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            UtilDAO.closeStatement(preparedStatement);
            UtilDAO.closeConnection(connection);
        }
    }

    public void deleteReview(int reviewId) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_REVIEW);
            preparedStatement.setString(1, String.valueOf(reviewId));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            UtilDAO.closeStatement(preparedStatement);
            UtilDAO.closeConnection(connection);
        }
    }
}
