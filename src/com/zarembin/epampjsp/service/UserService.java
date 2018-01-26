package com.zarembin.epampjsp.service;

import com.zarembin.epampjsp.dao.AuthenticationDAO;
import com.zarembin.epampjsp.dao.OrdersListDAO;
import com.zarembin.epampjsp.dao.RegistrationDAO;
import com.zarembin.epampjsp.dao.ReviewsDAO;
import com.zarembin.epampjsp.encryptor.Encryption;
import com.zarembin.epampjsp.entity.Order;
import com.zarembin.epampjsp.entity.Review;
import com.zarembin.epampjsp.entity.User;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.exception.ServiceException;

import java.util.List;
import java.util.Map;

public class UserService {
    public User findUserByEncryption(String login, String password) throws ServiceException {
        Encryption encryption = new Encryption();
        password = encryption.encrypt(password);
        AuthenticationDAO authenticationDAO = new AuthenticationDAO();
        try {
            return authenticationDAO.findUser(login,password);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(),e.getCause());
        }
    }

    public boolean singUpUserByEncryption(String userName, String password, String name, String lastname, String email, String cardNumber) throws ServiceException {
        Encryption encryption = new Encryption();
        password = encryption.encrypt(password);
        RegistrationDAO registrationDAO = new RegistrationDAO();
        try {
            return registrationDAO.insertNewUser(userName, password, name, lastname, email, cardNumber);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public List<Order> findOrdersByName(String userName) throws ServiceException {
        OrdersListDAO ordersListDAO = new OrdersListDAO();
        try {
            return ordersListDAO.findOrdersByName(userName);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public Map<String,Integer> findDishesByOrderId(int orderId) throws ServiceException {
        OrdersListDAO ordersListDAO = new OrdersListDAO();
        try {
            return ordersListDAO.findOrdersByOrderId(orderId);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public List<Review> findReviews() throws ServiceException {
        ReviewsDAO reviewsDAO = new ReviewsDAO();
        try {
            return reviewsDAO.findReviews();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public void insertReview(String userName, int mark, String textReview) throws ServiceException {
        ReviewsDAO reviewsDAO = new ReviewsDAO();
        try {
            reviewsDAO.insertNewReview(userName,mark,textReview);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

}
