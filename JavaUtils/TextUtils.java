package javautils;

/**
 * 文本工具类
 */
public final class TextUtils {

    /**
     * 私有构造防止实例化该工具类，反射创建时也会报错
     */
    private TextUtils() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    /**
     * 字符串是否为空
     */
    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    /**
     * 字符串是否非空
     */
    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    /**
     * 字符串去除两边空格是否为空
     */
    public static boolean isTrimEmpty(String s) {
        return isEmpty(s) || "".equals(s.trim());
    }

    /**
     * 字符串去除两边空格是否非空
     */
    public static boolean isNotTrimEmpty(String s) {
        return !isTrimEmpty(s);
    }

}
