package com.zarembin.epamjsp.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputTextValidator {

    private final String REG_EX_CARD_NUMBER = "\\d{4}";
    private final String REG_EX_MONEY = "\\d{1,6}(\\.\\d+)?";
    private final String REG_EX_EMAIL = "^([\\w-]+\\.)*[\\w-]+@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,6}$";
    private final String REG_EX_PASSWORD = "\\w+";
    private final String REG_EX_USER_NAME = "\\w+";
    private final String REG_EX_COMMENT="\\s*";


    public boolean isTextExist(String comment){
        return ((comment != null) && (!comment.isEmpty()) && (!comment.matches(REG_EX_COMMENT)));
    }

    public boolean isLogInValid(String login){
        return ((login != null) && (!login.isEmpty()) && (login.matches(REG_EX_USER_NAME)));
    }
    public boolean isPasswordValid(String password){
        return ((password != null) && (!password.isEmpty()) && (password.matches(REG_EX_PASSWORD)));
    }
    public boolean isEmailValid(String eMail){
        return ((eMail != null) && (!eMail.isEmpty()) && (eMail.matches(REG_EX_EMAIL)));
    }

    public boolean isCardNumberValid(String cardNumber){
        return ((cardNumber != null) && (!cardNumber.isEmpty()) && (cardNumber.matches(REG_EX_CARD_NUMBER)));
    }

    public boolean isMoneyValid(String money){
        return ((money != null) && (!money.isEmpty()) && (money.matches(REG_EX_MONEY)));
    }
}
