package com.zarembin.epampjsp.encryptor;

import com.zarembin.epampjsp.exception.ServiceException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {

    public String encrypt(String password) throws ServiceException {

        StringBuffer bufferPassword = new StringBuffer();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] encryptPassword =  messageDigest.digest(password.getBytes());
            for(byte b:encryptPassword){
                bufferPassword.append(String.format("%02X",b));
            }
            System.out.println();
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
        return bufferPassword.toString();

    }
}
