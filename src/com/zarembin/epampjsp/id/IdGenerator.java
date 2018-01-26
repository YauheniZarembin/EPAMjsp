package com.zarembin.epampjsp.id;

public class IdGenerator {
    private static int id = 10;
    public static int newId(){
        return ++id;
    }
}
