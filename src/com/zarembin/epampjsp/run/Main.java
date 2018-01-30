package com.zarembin.epampjsp.run;


import com.zarembin.epampjsp.validator.InputTextValidator;


public class Main {
    public static void main(String[] args) {
        String email = "ovf@esf.com";


        InputTextValidator inputTextValidator = new InputTextValidator();
        System.out.println(inputTextValidator.isEmailValid(email));


    }
}
