package com.zarembin.epampjsp.run;

import com.zarembin.epampjsp.dao.AuthenticationDAO;

public class Main {
    public static void main(String[] args) {
        AuthenticationDAO authenticationDAO = new AuthenticationDAO();
        System.out.println("отвeт " + authenticationDAO.authenticateUser("rak","qwe7"));
    }
}
