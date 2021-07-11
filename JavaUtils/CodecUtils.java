package javautils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 编码与解码操作工具类
 */
public final class CodecUtils {

    /**
     * 私有构造防止实例化该工具类，反射创建时也会报错
     */
    private CodecUtils() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    /**
     * 将 URL 编码
     */
    public static String encodeURL(String source) {
        String target;
        try {
            target = URLEncoder.encode(source, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("encode url failure", e);
        }
        return target;
    }

    /**
     * 将 URL 解码
     */
    public static String decodeURL(String source) {
        String target;
        try {
            target = URLDecoder.decode(source, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("decode url failure", e);
        }
        return target;
    }
}
