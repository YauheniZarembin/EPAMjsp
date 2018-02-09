package com.zarembin.epamjsp.pool;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String URL = "jdbc:mysql://localhost:3306/cafedb?autoReconnect=true&useSSL=false";
    private static final String USER_NAME="root";
    private static final String USER_PASSWORD ="2106057ZEa";

    private ArrayBlockingQueue<ProxyConnection> connectionQueue;
    private final int POOL_SIZE = 20;
    private static AtomicBoolean instanceCreated = new AtomicBoolean();
    private static ConnectionPool instance = null;
    private static ReentrantLock lock = new ReentrantLock();

    public static ConnectionPool getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    instanceCreated.set(true);
                }
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR  , e.getMessage());
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private ConnectionPool() throws SQLException {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        connectionQueue = new ArrayBlockingQueue<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            ProxyConnection connection = new ProxyConnection(ConnectionDB.getConnection(URL,USER_NAME,USER_PASSWORD));
            connectionQueue.offer(connection);
        }
    }



    public void releaseConnection(ProxyConnection connection) throws SQLException{
        if (connection.getAutoCommit()){
            try {
                connectionQueue.put(connection);
            } catch (InterruptedException e) {
                LOGGER.log(Level.ERROR  , e.getMessage());
            }
        }
    }


    public ProxyConnection getConnection(){
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR  , e.getMessage());
        }
        return connection;
    }


    public void destroyPool() {
        try {
            for (int i = 0; i < POOL_SIZE; i++) {
                ProxyConnection connection;
                connection = connectionQueue.take();
                connection.closeConnection();
            }
        } catch (InterruptedException | SQLException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        }
        deregisterDriver();
    }

    private void deregisterDriver() {
        Enumeration<Driver> enumDrivers = DriverManager.getDrivers();
        try {
            while (enumDrivers.hasMoreElements()) {
                Driver driver = enumDrivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        }
    }
}
