package com.rain.utils.datasource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Logger;

/**
 * DataSource 是 Java 实现线程池必须实现的一个接口
 */
public interface IRainDataSource extends DataSource {

    @Override
    Connection getConnection() throws SQLException;

    @Override
    Connection getConnection(String username, String password) throws SQLException;


    /**
     * 下面实现一个默认的空方法，防止子类继承时书写太多代码
     */

    @Override
    default PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    default void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    default void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    default int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    default ConnectionBuilder createConnectionBuilder() throws SQLException {
        return null;
    }

    @Override
    default <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    default boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    default Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    default ShardingKeyBuilder createShardingKeyBuilder() throws SQLException {
        return null;
    }

    void close(ConnectionProxy connectionProxy);
}
