package com.zarembin.epampjsp.run;


import com.zarembin.epampjsp.dao.OrdersListDAO;
import com.zarembin.epampjsp.entity.TypeOfDish;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.validator.InputTextValidator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    private static final Logger LOGGER = LogManager.getLogger();
    public static void main(String[] args) {
        String email = "ovf@esf.com";
        LOGGER.log(Level.INFO, "Add " + "in the basket");

        InputTextValidator inputTextValidator = new InputTextValidator();
        System.out.println(inputTextValidator.isEmailValid(email));

        System.out.println(TypeOfDish.valueOf("SOUP"));
        System.out.println(TypeOfDish.valueOf("BASIC"));

        OrdersListDAO ordersListDAO = new OrdersListDAO();
        try {
            System.out.println(ordersListDAO.findTodayOrders());
            System.out.println(ordersListDAO.findPastOrders());
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
