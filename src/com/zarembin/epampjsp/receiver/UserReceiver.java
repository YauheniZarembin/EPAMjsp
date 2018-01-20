//package com.zarembin.epampjsp.receiver;
//
//import com.zarembin.epampjsp.dao.AuthenticationDAO;
//import com.zarembin.epampjsp.dao.RegistrationDAO;
//import com.zarembin.epampjsp.service.LogInService;
//
//
//public class UserReceiver
//{
//
//
//    public boolean checkUser(String login, String password) {
//        LogInService logInService = new LogInService();
//        if (logInService.checkUserData(login,password)){
//            return
//        }
//        AuthenticationDAO authenticationDAO = new AuthenticationDAO();
//        return authenticationDAO.findUser(login,password);
//    }
//
//
//    public boolean registrationUser(String userName, String password,String name,String lastname,String email,String cardNumber){
//        RegistrationDAO registrationDAO = new RegistrationDAO();
//        return registrationDAO.insertNewUser(userName,password,name,lastname,email,cardNumber);
//    }
//}
