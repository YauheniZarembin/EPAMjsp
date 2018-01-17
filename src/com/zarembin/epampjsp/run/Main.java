package com.zarembin.epampjsp.run;


import com.zarembin.epampjsp.logic.UserReceiver;

public class Main {
    public static void main(String[] args) {
        UserReceiver userReceiver = new UserReceiver();
        System.out.println("отвeт " + userReceiver.checkUser("rak","qwe7"));
    }
}
