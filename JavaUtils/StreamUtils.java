package javautils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 流操作工具类
 */
public final class StreamUtils {

    /**
     * 私有构造防止实例化该工具类，反射创建时也会报错
     */
    private StreamUtils() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    /**
     * 从输入流中获取字符串
     */
    public static String getString(InputStream is) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("get string failure", e);
        }
        return stringBuilder.toString();
    }

}
