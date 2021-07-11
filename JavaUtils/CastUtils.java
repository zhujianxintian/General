package javautils;

/**
 * 转型操作类
 */
public final class CastUtils {

    /**
     * 私有构造防止实例化该工具类，反射创建时也会报错
     */
    private CastUtils() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    /**
     * 转为 String 型
     */
    public static String castString(Object obj) {
        return castString(obj, "");
    }

    /**
     * 转为 String 型（提供默认值）
     */
    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 转为 double 型
     */
    public static double castDouble(Object obj) {
        return castDouble(obj, 0);
    }

    /**
     * 转为 double 型（提供默认值）
     */
    public static double castDouble(Object obj, double defaultValue) {
        if (obj == null) {
            return defaultValue;
        }

        String s = castString(obj);
        if (TextUtils.isNotEmpty(s)) {
            return defaultValue;
        }

        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 转为 long 型
     */
    public static long castLong(Object obj) {
        return castLong(obj, 0);
    }

    /**
     * 转为 long 型（提供默认值）
     */
    public static long castLong(Object obj, long defaultValue) {
        if (obj == null) {
            return defaultValue;
        }

        String s = castString(obj);
        if (TextUtils.isNotEmpty(s)) {
            return defaultValue;
        }

        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 转为 int 型
     */
    public static int castInt(Object obj) {
        return castInt(obj, 0);
    }

    /**
     * 转为 int 型（提供默认值）
     */
    public static int castInt(Object obj, int defaultValue) {
        if (obj == null) {
            return defaultValue;
        }

        String s = castString(obj);
        if (TextUtils.isNotEmpty(s)) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 转为 boolean 型
     */
    public static boolean castBoolean(Object obj) {
        return castBoolean(obj, false);
    }

    /**
     * 转为 boolean 型（提供默认值）
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(castString(obj));
    }
}
