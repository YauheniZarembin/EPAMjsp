package com.zarembin.epampjsp.validator;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputTextValidator {

    private final String REG_EX_CARD_NUMBER = "\\d{4}";
    private final String REG_EX_MONEY = "\\d+(\\.\\d+)?";
    private final String REG_EX_EMAIL = "^([\\w-]+\\.)*[\\w-]+@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,6}$";
    private final String REG_EX_PASSWORD = "\\w+";
    private final String REG_EX_USER_NAME = "\\w+";
    private final String REG_EX_COMMENT="\\s*";





    public boolean isCommentValid(String comment){
        if ((comment==null) || (comment.isEmpty())){
            return false;
        }
        Pattern p = Pattern.compile(REG_EX_COMMENT);
        Matcher m = p.matcher(comment);
        return !m.matches();
    }

    public boolean isLogInValid(String login){
        if ((login==null) || (login.isEmpty())){
            return false;
        }
        Pattern p = Pattern.compile(REG_EX_USER_NAME);
        Matcher m = p.matcher(login);
        return m.matches();
    }
    public boolean isPasswordValid(String password){
        if ((password==null) || (password.isEmpty())){
            return false;
        }
        Pattern p = Pattern.compile(REG_EX_PASSWORD);
        Matcher m = p.matcher(password);
        return m.matches();
    }
    public boolean isEmailValid(String eMail){
        if ((eMail==null) || (eMail.isEmpty())){
            return false;
        }
        Pattern p = Pattern.compile(REG_EX_EMAIL);
        Matcher m = p.matcher(eMail);
        return m.matches();
    }

    public boolean isCardNumberValid(String cardNumber){
        if ((cardNumber==null) || (cardNumber.isEmpty())){
            return false;
        }
        Pattern p = Pattern.compile(REG_EX_CARD_NUMBER);
        Matcher m = p.matcher(cardNumber);
        return m.matches();
    }

    public boolean isMoneyValid(String money){
        if ((money==null) || (money.isEmpty())){
            return false;
        }
        Pattern p = Pattern.compile(REG_EX_MONEY);
        Matcher m = p.matcher(money);
        return m.matches();
    }
}
