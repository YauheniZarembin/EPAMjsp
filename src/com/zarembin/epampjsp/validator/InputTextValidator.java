package com.zarembin.epampjsp.validator;

public class InputTextValidator {

    private final String REG_EX_CARD_NUMBER = "\\d{4}";
    private final String REG_EX_EMAIL = "^((\\w|[-+])+(\\.[\\w-]+)*@[\\w-]+((\\.[\\d\\p{Alpha}]+)*(\\.\\p{Alpha}{2,})*)*)$";
    private final String REG_EX_PASSWORD = "";
    private final String REG_EX_USER_NAME = "";

    public boolean isLogInValid(String login){
        return false;
    }

    public boolean isPasswordValid(String password){
        return false;
    }

    public boolean isEmailValid(String eMail){
        return false;
    }

    public boolean isCardNumberValid(String cardNumber){
        return false;
    }


}
