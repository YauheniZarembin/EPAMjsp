package com.zarembin.epampjsp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDB {
    private static String url = "jdbc:mysql://localhost:3306/cafedb?autoReconnect=true&useSSL=false";
    private static String userName="root";
    private static String userPassword="2106057ZEa";
    /////////////// убрать public и в пакет proxy
    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        properties.put("user", userName);
        properties.put("password", userPassword);
        properties.put("autoReconnect", "true");
        properties.put("characterEncoding", "UTF-8");
        properties.put("useUnicode", "true");
        return DriverManager.getConnection(url, properties);
    }
}
