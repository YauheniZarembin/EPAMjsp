package com.zarembin.epamjsp.run;


import com.zarembin.epamjsp.dao.UserListDAO;
import com.zarembin.epamjsp.entity.TypeOfDish;
import com.zarembin.epamjsp.exception.DAOException;
import com.zarembin.epamjsp.validator.InputTextValidator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;


public class Main {
    private static final Logger LOGGER = LogManager.getLogger();
    public static void main(String[] args) {
        String email = "ovf@esf.com";

        LOGGER.log(Level.INFO, "1111111");

        InputTextValidator inputTextValidator = new InputTextValidator();
        System.out.println(inputTextValidator.isEmailValid(email));

        System.out.println(inputTextValidator.isMoneyValid("32233.34"));

    }
}
