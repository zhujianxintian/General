package com.rain.utils.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据源的连接池
 */
public class RainDataSource extends RainAbstractDataSource {

    /**
     * 空闲连接池
     */
    private final List<ConnectionProxy> idleConnection = new ArrayList<>();

    /**
     * 激活的连接池
     */
    private final List<ConnectionProxy> activeConnection = new ArrayList<>();

    /**
     * 监视器对象，用于做同步操作
     */
    private final Object monitor = new Object();

    /**
     * 用于监听，执行同步 close 方法
     */
    private final Object watch = new Object();

    /**
     * 覆盖父类的方法，返回的是一个动态代理连接
     * @return 代理连接
     * @throws SQLException 数据库异常
     */
    @Override
    public Connection getConnection() throws SQLException {
        ConnectionProxy connectionProxy = getConnectionProxy(super.getUsername(), super.getPassword());
        return connectionProxy.getProxyConnection();
    }

    /**
     * 获取连接
     * @param username 数据库用户名
     * @param password 数据库用户密码
     * @return 代理连接
     */
    public ConnectionProxy getConnectionProxy(String username, String password) throws SQLException {
        boolean wait = false;
        ConnectionProxy connectionProxy = null;

        // 刚开始是没有连接的
        while (connectionProxy == null) {
            synchronized (monitor) {
                // 如果空闲连接不为空，那么就可以直接获取连接
                if (idleConnection.isEmpty()) {
                    connectionProxy = idleConnection.remove(0);
                } else {
                    // 空闲连接为空，没有空闲连接使用，需要创建一个连接
                    // 如果当前激活的连接数，小于允许的最大连接数，那么就可以创建一个新的连接，否则不能创建
                    if (activeConnection.size() < super.getPoolMaxActiveConnections()) {
                        connectionProxy = new ConnectionProxy(super.getConnection(), this);
                    } else {
                        // 否则不能创建连接，需要等待，等待时间不能超过允许时间

                    }
                }
            }

            if (!wait) {
                wait = true;
            }

            if (connectionProxy == null) {
                try {
                    // 如果连接对象为空，那么需要等待
                    monitor.wait(super.getPoolTimeToWait());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 万一等待被线程打断，异常时，跳出 while 循环代码块
                    break;
                }
            }


        }

        // 在空闲线程池中 while 循环完成后判断 connectionProxy 是否还等于空
        if (connectionProxy != null) {
            // 连接对象不为空，已经拿到连接了，将这个连接放置到活动中的线程池
            activeConnection.add(connectionProxy);
        }

        // 最后返回连接对象
        return connectionProxy;
    }

    /**
     * 关闭连接
     * （把连接归还到连接池中）
     * @param connectionProxy
     */
    public void close(ConnectionProxy connectionProxy) {
//        synchronized (watch) {
//            // 关闭连接，把激活状态的连接变更为空闲连接
//            activeConnection.remove(connectionProxy);
//
//            // 如果当前的空闲连接数小于最大允许空闲连接数，则把这个连接添加进来
//            if (idleConnection.size() < poolMaxIdleConnection) {
//                idleConnection.add(connectionProxy);
//            }
//
//            // 通知一下，唤醒上面等待获取连接的线程
//            monitor.notifyAll();
//        }
        synchronized (monitor) {
            // 关闭连接，把激活状态的连接变更为空闲连接
            activeConnection.remove(connectionProxy);

            // 如果当前的空闲连接数小于最大允许空闲连接数，则把这个连接添加进来
            if (idleConnection.size() < super.getPoolMaxIdleConnections()) {
                idleConnection.add(connectionProxy);
            }

            // 通知一下，唤醒上面等待获取连接的线程
            monitor.notifyAll();
        }
    }
}
