package com.zarembin.epampjsp.run;

import com.zarembin.epampjsp.dao.AuthenticationDAO;
import com.zarembin.epampjsp.dao.MenuDAO;
import com.zarembin.epampjsp.dao.MoneyDAO;
import com.zarembin.epampjsp.encryptor.Encryption;
import com.zarembin.epampjsp.entity.TypeOfDish;
import com.zarembin.epampjsp.entity.User;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.id.IdGenerator;
import com.zarembin.epampjsp.service.UserService;
import com.zarembin.epampjsp.validator.InputTextValidator;
import jdk.internal.org.objectweb.asm.Handle;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String user_name = "dfk";
        String password = "fdkml_gtrt";
        String card = "4326";
        String email = "ovf@mai6l.ru";
        String e = "http://localhost:8081/jsp/login.jsp";
        String mo = "0324323423,433333333";


        InputTextValidator inputTextValidator = new InputTextValidator();
        int dd = 23;
        dd++;
        System.out.println(dd);


        LocalDateTime ff = LocalDateTime.of(2018,2,3,22,00);
        System.out.println(new UserService().checkDateTimeOrder(ff));

//        User user = null;
//        try {
//            user = new UserService().findUserByEncryption("hurinovich", "qwe5");
//        } catch (ServiceException e1) {
//            e1.printStackTrace();
//        }
//
//        try {
//            new MoneyDAO().topUpUserMoney(new BigDecimal(3300),new BigDecimal(322),user);
//        } catch (DAOException e1) {
//            e1.printStackTrace();
//        }
    }
}
