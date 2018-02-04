package com.zarembin.epampjsp.service;

import com.zarembin.epampjsp.dao.MenuDAO;
import com.zarembin.epampjsp.dao.OrdersListDAO;
import com.zarembin.epampjsp.entity.Dish;
import com.zarembin.epampjsp.entity.TypeOfDish;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.exception.ServiceException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuService {

    private static final String DIR_PATH = "\\resource\\image\\";

    public Map<Dish,Integer> subDish(Map<Dish,Integer> order,Dish dish) throws ServiceException {
        if (order.get(dish) == 1) {
            order.remove(dish);
        } else {
            order.replace(dish, order.get(dish) - 1);
        }
        return order;
    }

    public Map<Dish,Integer> addDish(Map<Dish,Integer> order,Dish dish) throws ServiceException {
        if (order == null) {
            order = new HashMap<>();
        }
        if (order.containsKey(dish)) {
            order.replace(dish, order.get(dish) + 1);
        } else {
            order.put(dish, 1);
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


    public List<Dish> findAllDishes() throws ServiceException {
        MenuDAO menuDAO = new MenuDAO();
        try {
            return menuDAO.findAllDishes();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public List<Dish> findDishesByType(TypeOfDish typeOfDish) throws ServiceException {
        MenuDAO menuDAO = new MenuDAO();
        try {
            return menuDAO.findDishesByType(typeOfDish);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public Dish findDishByName(String dishName) throws ServiceException {
        MenuDAO menuDAO = new MenuDAO();
        try {
            return menuDAO.findDishByName(dishName);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public void changeDishPrice(String dishName, String newPrice) throws ServiceException {
        MenuDAO menuDAO = new MenuDAO();
        try {
            menuDAO.changeDishPrice(dishName,newPrice);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
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
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }


    public void uploadDishPicture(String dishName, String fileName){
        MenuDAO menuDAO = new MenuDAO();
        try {
            menuDAO.changeDishPicture(dishName,DIR_PATH + fileName);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void insertNewDish(String dishName, TypeOfDish typeOfDish , String dishPrice){
        MenuDAO menuDAO = new MenuDAO();
        try {
            menuDAO.insertNewDish(dishName,typeOfDish,dishPrice);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

}