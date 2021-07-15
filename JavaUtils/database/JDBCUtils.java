package com.rain.utils.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JDBCUtils {

    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties properties = PropertiesUtils.load("");
        try {
            Class.forName(properties.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("jdbc driver is not found", e);
        }
        URL = properties.getProperty("jdbc.url");
        USERNAME = properties.getProperty("jdbc.username");
        PASSWORD = properties.getProperty("jdbc.password");
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public <T> List<T> findAll(String sql, Mapper<T> mapper, Object...objects) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < objects.length; i++) {
            preparedStatement.setObject(i + 1, objects[i]);
        }
        ResultSet resultSet = preparedStatement.executeQuery();

        List<T> list = new ArrayList<>();
        while (resultSet.next()) {
            T entity = mapper.mapRow(resultSet);
            list.add(entity);
        }
        return list;
    }

    public <T> T findOne(String sql, Mapper<T> mapper, Object...objects) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < objects.length; i++) {
            preparedStatement.setObject(i + 1, objects[i]);
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        T entity = null;
        if (resultSet.next()) {
            entity = mapper.mapRow(resultSet);
        }
        return entity;
    }

    public void close(AutoCloseable...closeables) {
        for (AutoCloseable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    throw new RuntimeException(closeable.getClass() + " 关闭失败 ", e);
                }
            }
        }
    }
}
