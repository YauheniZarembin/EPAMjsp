package com.zarembin.epampjsp.encryptor;

public class Encryption {
    private static final String ENCODE_PART = "blinov";

    public String encrypt(String password){
        String data = password + ENCODE_PART;
        return data;


        /////  поменять на нормальный алгоритм
    }
}
