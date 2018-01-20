package com.zarembin.epampjsp.service;

import com.zarembin.epampjsp.dao.AuthenticationDAO;
import com.zarembin.epampjsp.dao.RegistrationDAO;
import com.zarembin.epampjsp.encryptor.Encryption;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.validator.InputTextValidator;

public class SignUpService {

    public boolean singUpUserByEncryption(String userName, String password, String name, String lastname, String email, String cardNumber) throws ServiceException {
        Encryption encryption = new Encryption();
        password = encryption.encrypt(password);
        RegistrationDAO registrationDAO = new RegistrationDAO();
        try {
            return registrationDAO.insertNewUser(userName, password, name, lastname, email, cardNumber);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public boolean checkUserData(String userName, String password, String name, String lastname, String email, String cardNumber){
        InputTextValidator inputTextValidator = new InputTextValidator();
        return inputTextValidator.isPasswordValid(password)&& inputTextValidator.isLogInValid(userName)
                && inputTextValidator.isEmailValid(email) && inputTextValidator.isCardNumberValid(cardNumber);
    }
}
