package com.zarembin.epampjsp.service;

import com.zarembin.epampjsp.dao.MenuDAO;
import com.zarembin.epampjsp.dao.OrdersListDAO;
import com.zarembin.epampjsp.dao.UserListDAO;
import com.zarembin.epampjsp.entity.Dish;
import com.zarembin.epampjsp.entity.Order;
import com.zarembin.epampjsp.entity.User;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.exception.ServiceException;

import java.util.List;
import java.util.Map;

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


    public List<Order> findTodayOrders() throws ServiceException {
        OrdersListDAO ordersListDAO = new OrdersListDAO();
        try {
            return ordersListDAO.findTodayOrders();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public List<Order> findFutureOrders() throws ServiceException {
        OrdersListDAO ordersListDAO = new OrdersListDAO();
        try {
            return ordersListDAO.findFutureOrders();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public List<Order> findPastOrders() throws ServiceException {
        OrdersListDAO ordersListDAO = new OrdersListDAO();
        try {
            return ordersListDAO.findPastOrders();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public void deleteOrder(String orderId) throws ServiceException {
        OrdersListDAO ordersListDAO = new OrdersListDAO();
        MenuDAO menuDAO = new MenuDAO();
        try {
            Order order = ordersListDAO.findOrderById(orderId);
            ordersListDAO.deleteOrder(orderId);
            for(Map.Entry<Dish,Integer> entry : order.getDishes().entrySet()){
                if (!ordersListDAO.isDishInOrders(entry.getKey().getDishName())) {
                    if(menuDAO.isDishNoMore(entry.getKey().getDishName())) {
                        menuDAO.deleteDish(entry.getKey().getDishName());
                    }
                }
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public void deleteLoyaltyPoints(String userName) throws ServiceException {
        UserListDAO userListDAO = new UserListDAO();
        try {
            int loyaltyPoints = userListDAO.findUserPoints(userName) - 10;
            String ban = loyaltyPoints < 0 ? "1" : "0";
            userListDAO.deleteLoyaltyPoint(ban,loyaltyPoints,userName);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }
}
