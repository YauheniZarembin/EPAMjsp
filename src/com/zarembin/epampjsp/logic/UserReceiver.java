package com.zarembin.epampjsp.logic;

import com.zarembin.epampjsp.dao.AuthenticationDAO;
import com.zarembin.epampjsp.dao.RegistrationDAO;


public class UserReceiver
{


    public boolean checkUser(String login, String password) {
        AuthenticationDAO authenticationDAO = new AuthenticationDAO();
        return authenticationDAO.checkUser(login,password);
    }


    public boolean registrationUser(String userName, String password,String name,String lastname,String email,String cardNumber){
        RegistrationDAO registrationDAO = new RegistrationDAO();
        return registrationDAO.insertNewUser(userName,password,name,lastname,email,cardNumber);
    }
}
