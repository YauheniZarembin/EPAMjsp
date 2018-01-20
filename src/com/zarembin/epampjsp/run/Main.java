package com.zarembin.epampjsp.run;

import com.zarembin.epampjsp.dao.MenuDAO;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.validator.InputTextValidator;

public class Main {
    public static void main(String[] args) {
        String user_name = "dfk";
        String password = "fdkml_gtrt";
        String card = "4326";
        String email = "ovf@mai6l.ru";

        InputTextValidator inputTextValidator = new InputTextValidator();
        System.out.println(inputTextValidator.isLogInValid(user_name));
        System.out.println(inputTextValidator.isPasswordValid(password));
        System.out.println(inputTextValidator.isCardNumberValid(card));
        System.out.println(inputTextValidator.isEmailValid(email));


        MenuDAO menuDAO = new MenuDAO();
        try {
            System.out.println(menuDAO.findAllDishes());
        } catch (DAOException e) {
        }
    }
}
