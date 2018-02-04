package com.zarembin.epampjsp.run;


import com.zarembin.epampjsp.dao.OrdersListDAO;
import com.zarembin.epampjsp.dao.UserListDAO;
import com.zarembin.epampjsp.entity.Dish;
import com.zarembin.epampjsp.entity.Order;
import com.zarembin.epampjsp.entity.TypeOfDish;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.service.AdminService;
import com.zarembin.epampjsp.validator.InputTextValidator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;


public class Main {
    private static final Logger LOGGER = LogManager.getLogger();
    public static void main(String[] args) {
        String email = "ovf@esf.com";
        LOGGER.log(Level.INFO, "Add " + "in the basket");

        InputTextValidator inputTextValidator = new InputTextValidator();
        System.out.println(inputTextValidator.isEmailValid(email));

        System.out.println(TypeOfDish.valueOf("SOUP"));
        System.out.println(TypeOfDish.valueOf("BASIC"));

        try {
            System.out.println(new UserListDAO().findUserPoints("germa"));
        } catch (DAOException e) {
            e.printStackTrace();
        }

    }
}
