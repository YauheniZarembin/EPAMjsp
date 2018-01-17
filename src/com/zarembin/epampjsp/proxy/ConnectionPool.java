package com.zarembin.epampjsp.proxy;

import com.zarembin.epampjsp.util.ConnectionDB;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
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
                e.printStackTrace();
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
            ProxyConnection connection = new ProxyConnection(ConnectionDB.getConnection());
            connectionQueue.offer(connection);
        }
    }



    public void releaseConnection(ProxyConnection connection) throws SQLException{
        if (connection.getAutoCommit()){
            try {
                connectionQueue.put(connection);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public ProxyConnection getConnection(){
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();//connection->daoexception->receiver-service->command_exception->error_page
        }
        return connection;
    }


    public void destroyConnection() throws InterruptedException, SQLException {
        for (int i=0; i<POOL_SIZE; i++){
            ProxyConnection connection = connectionQueue.take();
            connection.closeConnection();
        }
    }
}
