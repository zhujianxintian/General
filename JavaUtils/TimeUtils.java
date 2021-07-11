package javautils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间工具类
 */
public class TimeUtils {

    public static final String YTD = "yyyy-MM-dd";
    public static final String TIME12 = "hh:mm:ss";
    public static final String TIME24 = "hh:mm:ss";
    public static final String YTD_TIME24 = YTD + " " + TIME24;
    public static final String YTD_TIME12 = YTD + " " + TIME12;

    /**
     * 私有构造防止实例化该工具类，反射创建时也会报错
     */
    private TimeUtils() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static String now(String format) {
        return DateTimeFormatter.ofPattern(format).format(LocalDateTime.now());
    }

    public static LocalDateTime now(String time, String format) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(format));
    }

}
