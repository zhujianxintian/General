package com.rain.utils.database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性工具类
 */
public final class PropertiesUtils {

    /**
     * 私有构造防止实例化该工具类，反射创建时也会报错
     */
    private PropertiesUtils() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    /**
     * 通过文件路径加载配置文件
     */
    public static Properties load(String filePath) {
        Properties properties;
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        try {
            if (inputStream == null) {
                throw new FileNotFoundException(filePath + " file is not found");
            }
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("load properties file failure", e);
        } finally {
            close(inputStream);
        }
        return properties;
    }

    /**
     * 关闭操作
     */
    public static void close(AutoCloseable... closeables) {
        for (AutoCloseable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
