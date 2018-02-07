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

        System.out.println(TypeOfDish.valueOf("SOUP"));
        System.out.println(TypeOfDish.valueOf("BASIC"));


        BigDecimal a = new BigDecimal("2.5"); // цифра слева от 5 чётная, поэтому округление вниз

        BigDecimal b = new BigDecimal("1.5"); // цифра слева от 5 нечётная, поэтому округление вверх

        a.setScale(3, BigDecimal.ROUND_HALF_EVEN).toString(); // => 2

        b.setScale(3, BigDecimal.ROUND_HALF_EVEN).toString(); // => 2

        System.out.println(a);
        System.out.println(b);



    }
}
