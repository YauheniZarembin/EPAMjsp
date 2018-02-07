package com.zarembin.epamjsp.encryptor;

import com.zarembin.epamjsp.exception.ServiceException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Encryption {

    private static final Logger LOGGER = LogManager.getLogger();

    public String encrypt(String password) throws ServiceException {

        StringBuffer bufferPassword = new StringBuffer();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] encryptPassword =  messageDigest.digest(password.getBytes());
            for(byte b:encryptPassword){
                bufferPassword.append(String.format("%02X",b));
            }
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.ERROR  , e.getMessage());
        }
        return bufferPassword.toString();

    }
}
