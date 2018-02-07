package com.zarembin.epamjsp.service;

import com.zarembin.epamjsp.dao.MenuDAO;
import com.zarembin.epamjsp.dao.OrdersListDAO;
import com.zarembin.epamjsp.entity.Dish;
import com.zarembin.epamjsp.entity.TypeOfDish;
import com.zarembin.epamjsp.exception.DAOException;
import com.zarembin.epamjsp.exception.ServiceException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuService {

    private static final String DIR_PATH = "\\resource\\image\\";
    private static final int MIN_NUMBER_OF_DISH_IN_BASKET = 1;
    private static final int ONE_SERVING = 1;

    public Map<Dish,Integer> subDish(Map<Dish,Integer> order,Dish dish) throws ServiceException {
        if (order.get(dish) == MIN_NUMBER_OF_DISH_IN_BASKET) {
            order.remove(dish);
        } else {
            order.replace(dish, order.get(dish) - ONE_SERVING);
        }
        return order;
    }

    public Map<Dish,Integer> addDish(Map<Dish,Integer> order,Dish dish) throws ServiceException {
        if (order == null) {
            order = new HashMap<>();
        }
        if (order.containsKey(dish)) {
            order.replace(dish, order.get(dish) + ONE_SERVING);
        } else {
            order.put(dish, MIN_NUMBER_OF_DISH_IN_BASKET);
        }
        return order;
    }

    public BigDecimal addCost(BigDecimal oldCost , Dish dish){
        if (oldCost == null){
            oldCost = new BigDecimal(0);
        }
        oldCost = oldCost.add(dish.getPrice());
        return oldCost;
    }

    public List<Dish> findDishesByType(TypeOfDish typeOfDish) throws ServiceException {
        MenuDAO menuDAO = new MenuDAO();
        try {
            return menuDAO.findDishesByType(typeOfDish);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public Dish findDishByName(String dishName) throws ServiceException {
        MenuDAO menuDAO = new MenuDAO();
        try {
            return menuDAO.findDishByName(dishName);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void changeDishPrice(String dishName, String newPrice) throws ServiceException {
        MenuDAO menuDAO = new MenuDAO();
        try {
            menuDAO.changeDishPrice(dishName,newPrice);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void deleteFromMenu(String dishName) throws ServiceException {
        MenuDAO menuDAO = new MenuDAO();
        OrdersListDAO ordersListDAO = new OrdersListDAO();
        try {
            if (ordersListDAO.isDishInOrders(dishName)){
                menuDAO.changeIsNoMore(dishName);
            }
            else{
                menuDAO.deleteDish(dishName);
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }


    public void uploadDishPicture(String dishName, String fileName) throws ServiceException {
        MenuDAO menuDAO = new MenuDAO();
        try {
            menuDAO.changeDishPicture(dishName,DIR_PATH + fileName);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void insertNewDish(String dishName, TypeOfDish typeOfDish , String dishPrice) throws ServiceException {
        MenuDAO menuDAO = new MenuDAO();
        try {
            menuDAO.insertNewDish(dishName,typeOfDish,dishPrice);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}