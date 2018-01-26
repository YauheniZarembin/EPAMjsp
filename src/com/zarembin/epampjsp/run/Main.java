package com.zarembin.epampjsp.run;

import com.zarembin.epampjsp.dao.AuthenticationDAO;
import com.zarembin.epampjsp.dao.MenuDAO;
import com.zarembin.epampjsp.encryptor.Encryption;
import com.zarembin.epampjsp.entity.TypeOfDish;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.id.IdGenerator;
import com.zarembin.epampjsp.validator.InputTextValidator;
import jdk.internal.org.objectweb.asm.Handle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        InputTextValidator inputTextValidator = new InputTextValidator();
        System.out.println(inputTextValidator.isLogInValid(user_name));
        System.out.println(inputTextValidator.isPasswordValid(password));
        System.out.println(inputTextValidator.isCardNumberValid(card));
        System.out.println(inputTextValidator.isEmailValid(email));


        System.out.println(LocalTime.now());
        System.out.println(LocalDateTime.now());
        System.out.println(LocalDateTime.of(LocalDate.now(),LocalTime.now()));

        System.out.println();

        Map<String,Integer>  f=new HashMap<>();

        f.put("11" , 1);
        f.put("11" , 2);
        f.put("33" , 3);
        f.replace("11",f.get("33")+1);

        System.out.println(f);





    }
}
