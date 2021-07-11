package javautils;

/**
 * 数组工具类
 */
public final class ArrayUtils {

    /**
     * 私有构造防止实例化该工具类，反射创建时也会报错
     */
    private ArrayUtils() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    /**
     * 获取数组长度
     */
    public static int getLength(Object[] objects) {
        if (objects == null) {
            return 0;
        }
        return objects.length;
    }

    /**
     * 判断数组是否为空
     */
    public static boolean isEmpty(Object[] array) {
        return getLength(array) == 0;
    }

    /**
     * 判断数组是否非空
     */
    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }
}
