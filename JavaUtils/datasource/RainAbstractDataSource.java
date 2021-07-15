package com.rain.utils.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class RainAbstractDataSource implements IRainDataSource {

    private String url;

    private String driver;

    private String username;

    private String password;

    /**
     * 最大正在使用的连接数
     */
    private int poolMaxActiveConnections = 10;

    /**
     * 最大空闲的连接数
     */
    private int poolMaxIdleConnections = 10;

    /**
     * 从连接池中获取连接最大等待时间（毫秒）
     */
    private int poolTimeToWait = 30000;

    @Override
    public Connection getConnection() throws SQLException {
        return getConnection(username, password);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getConnection0(username, password);
    }

    private Connection getConnection0(String username, String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoolMaxActiveConnections() {
        return poolMaxActiveConnections;
    }

    public void setPoolMaxActiveConnections(int poolMaxActiveConnection) {
        this.poolMaxActiveConnections = poolMaxActiveConnection;
    }

    public int getPoolMaxIdleConnections() {
        return poolMaxIdleConnections;
    }

    public void setPoolMaxIdleConnections(int poolMaxIdleConnection) {
        this.poolMaxIdleConnections = poolMaxIdleConnection;
    }

    public int getPoolTimeToWait() {
        return poolTimeToWait;
    }

    public void setPoolTimeToWait(int poolTimeToWait) {
        this.poolTimeToWait = poolTimeToWait;
    }
}
