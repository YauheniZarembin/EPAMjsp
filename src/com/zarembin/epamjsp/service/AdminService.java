package com.zarembin.epamjsp.service;

import com.zarembin.epamjsp.dao.MenuDAO;
import com.zarembin.epamjsp.dao.OrdersListDAO;
import com.zarembin.epamjsp.dao.UserListDAO;
import com.zarembin.epamjsp.entity.Dish;
import com.zarembin.epamjsp.entity.Order;
import com.zarembin.epamjsp.entity.User;
import com.zarembin.epamjsp.exception.DAOException;
import com.zarembin.epamjsp.exception.ServiceException;

import java.util.List;
import java.util.Map;

public class AdminService {

    private static final String USER_NO_BAN = "0";
    private static final String USER_BAN = "1";
    private static final int MIN_POINTS = 0 ;
    private static final int FINE = 10 ;


    public void changeUserBan(String userName , boolean ban) throws ServiceException {
        UserListDAO userListDAO = new UserListDAO();
        try {
            String banString = ban ? USER_NO_BAN : USER_BAN ;
            userListDAO.changeUserBan(userName,banString);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<User> findAllUsers() throws ServiceException {
        UserListDAO userListDAO = new UserListDAO();
        try {
            return userListDAO.findAllUsers();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Order> findTodayOrders() throws ServiceException {
        OrdersListDAO ordersListDAO = new OrdersListDAO();
        try {
            return ordersListDAO.findTodayOrders();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Order> findFutureOrders() throws ServiceException {
        OrdersListDAO ordersListDAO = new OrdersListDAO();
        try {
            return ordersListDAO.findFutureOrders();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Order> findPastOrders() throws ServiceException {
        OrdersListDAO ordersListDAO = new OrdersListDAO();
        try {
            return ordersListDAO.findPastOrders();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
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
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void deleteLoyaltyPoints(String userName) throws ServiceException {
        UserListDAO userListDAO = new UserListDAO();
        try {
            int loyaltyPoints = userListDAO.findUserPoints(userName) - FINE;
            String ban = loyaltyPoints < MIN_POINTS ? USER_BAN : USER_NO_BAN;
            userListDAO.deleteLoyaltyPoint(ban,loyaltyPoints,userName);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
