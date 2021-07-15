package com.rain.utils.datasource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * 采用动态代理实现对数据库连接的代理
 */
public class ConnectionProxy implements InvocationHandler {

    /**
     * 真实的连接
     */
    private Connection realConnection;

    /**
     * 代理的连接
     */
    private Connection proxyConnection;

    /**
     * 持有数据源对象
     */
    private IRainDataSource rainDataSource;

    /**
     * 通过构造方法初始化真实的连接和数据源
     *
     * @param realConnection 真实的连接
     * @param rainDataSource 数据源
     */
    public ConnectionProxy(Connection realConnection, IRainDataSource rainDataSource) {
        this.realConnection = realConnection;
        this.rainDataSource = rainDataSource;

        // 初始化代理连接
        this.proxyConnection = (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(), new Class[]{Connection.class}, this);
    }

    /**
     * 当调用 Connection 的对象里面的方法时，首先会被该 invoke 方法拦截
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 获取当前 Connection 的对象调用方法的名称，用于判断
        String methodName = method.getName();

        if ("close".equalsIgnoreCase(methodName)) {
            // 如果调用的是 close 方法，则把连接归还到连接池中
            rainDataSource.close(this);
            return null;
        } else {
            // 如果不是 close 方法，则直接调用并返回结果即可
            return method.invoke(realConnection, args);
        }
    }

    public Connection getRealConnection() {
        return realConnection;
    }

    public void setRealConnection(Connection realConnection) {
        this.realConnection = realConnection;
    }

    public Connection getProxyConnection() {
        return proxyConnection;
    }

    public void setProxyConnection(Connection proxyConnection) {
        this.proxyConnection = proxyConnection;
    }

    public IRainDataSource getRainDataSource() {
        return rainDataSource;
    }

    public void setRainDataSource(IRainDataSource rainDataSource) {
        this.rainDataSource = rainDataSource;
    }
}
