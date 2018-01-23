package com.zarembin.epampjsp.service;

import com.zarembin.epampjsp.dao.MenuDAO;
import com.zarembin.epampjsp.entity.Dish;
import com.zarembin.epampjsp.entity.TypeOfDish;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class MenuService {
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

}