package javautils;

import java.util.Collection;

/**
 * 集合工具类
 */
public final class CollectionUtils {

    /**
     * 私有构造防止实例化该工具类，反射创建时也会报错
     */
    private CollectionUtils() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    /**
     * 集合是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 集合是否非空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}
