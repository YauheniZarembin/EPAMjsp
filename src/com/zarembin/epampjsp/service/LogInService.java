package com.zarembin.epampjsp.service;

import com.zarembin.epampjsp.dao.AuthenticationDAO;
import com.zarembin.epampjsp.encryptor.Encryption;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.validator.InputTextValidator;

public class LogInService {

    public boolean findUserByEncryption(String login, String password) throws ServiceException {
        Encryption encryption = new Encryption();
        password = encryption.encrypt(password);
        AuthenticationDAO authenticationDAO = new AuthenticationDAO();
        try {
            return authenticationDAO.findUser(login,password);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(),e.getCause());
        }
    }
}
