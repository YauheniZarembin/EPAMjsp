package com.zarembin.epampjsp.service;

import com.zarembin.epampjsp.dao.OrdersListDAO;
import com.zarembin.epampjsp.dao.UserListDAO;
import com.zarembin.epampjsp.entity.Order;
import com.zarembin.epampjsp.entity.User;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.exception.ServiceException;

import java.util.List;

public class AdminService {


    public void changeUserBan(String userName , boolean ban) throws ServiceException {
        UserListDAO userListDAO = new UserListDAO();
        try {
            String banString = ban ? "0" : "1";
            userListDAO.changeUserBan(userName,banString);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public void changeReceivedOrder(String orderId) throws ServiceException {
        OrdersListDAO ordersListDAO = new OrdersListDAO();
        try {
            System.out.println(orderId+"receiver");
            ordersListDAO.changeOrderReceived(orderId);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public List<User> findAllUsers() throws ServiceException {
        UserListDAO userListDAO = new UserListDAO();
        try {
            return userListDAO.findAllUsers();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }


    public List<Order> findAllOrders() throws ServiceException {
        OrdersListDAO ordersListDAO = new OrdersListDAO();
        try {
            return ordersListDAO.findAllOrders();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }
}
