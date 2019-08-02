package oysd.com.trade_app.util;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import oysd.com.trade_app.common.InfoProvider;

/**
 * Data and time utils.
 * Created by Liam on 2018/8/15
 */
public final class DateTimeUtils {

    private DateTimeUtils() {
        // Prevents from being instantiated.
    }

    public static final long SECOND = 1000;
    public static final long MINUTE = 60 * SECOND;
    public static final long HALF_HOUR = 30 * MINUTE;
    public static final long HOUR = 60 * MINUTE;
    public static final long DAY = 24 * HOUR;
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_DOT = "yyyy.MM.dd";
    public static final String MM_DD = "MM-dd";
    public static final String HH_MM_SS = "HH:mm:ss";

    public static long now() {
        return System.currentTimeMillis();
    }

    /**
     * Formats "now" into string using default pattern.
     */
    public static String format() {
        return format(now());
    }

    /**
     * Formats a Long timestamp into String using default pattern.
     */
    public static String format(long timestamp) {
        return format(timestamp, DEFAULT_PATTERN);
    }

    /**
     * Formats a Long timestamp into String.
     *
     * @param timestamp timestamp
     * @param pattern   pattern
     * @return a formatted String
     */
    public static String format(long timestamp, String pattern) {
        Timestamp ts = new Timestamp(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        return sdf.format(ts);
    }

    /**
     * 获取当前的日期，使用 yyyy.MM.dd 进行格式化。
     */
    public static String getCurrentDateWithDot() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD_DOT, Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    /**
     * @return 一个月之后的日期，yyyy.MM.dd 格式。
     */
    public static String getNextMonthDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        return calendarToDate(calendar);
    }

    /**
     * @return 一个季度之后的日期，yyyy.MM.dd 格式。
     */
    public static String getNextSeasonDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 3);
        return calendarToDate(calendar);
    }

    /**
     * @return 一年之后的日期，yyyy.MM.dd 格式。
     */
    public static String getNextYearDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
        return calendarToDate(calendar);
    }

    // 将 calendar 转成对应的 yyyy.MM.dd 日期格式。
    private static String calendarToDate(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "." + (month + 1) + "." + day;
    }

    /**
     * 将服务返回的 "2018-10-22 00:00:00" 日期,只取其中日期 "2018-10-22" .
     */
    @Deprecated
    public static String getDateFromOriginal(@NonNull String originalDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN, Locale.getDefault());
        try {
            Date date = sdf.parse(originalDate);
            SimpleDateFormat sdf2 = new SimpleDateFormat(YYYY_MM_DD, Locale.getDefault());
            return sdf2.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 将服务返回的 "2018-10-22 00:00:00" 日期,转成对应的 "2018.10.22" .
     */
    @Deprecated
    public static String getDateInDotFromOriginal(@NonNull String originalDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN, Locale.getDefault());
        try {
            Date date = sdf.parse(originalDate);
            SimpleDateFormat sdf2 = new SimpleDateFormat(YYYY_MM_DD_DOT, Locale.getDefault());
            return sdf2.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 将系统返回的时间字符串，更正为 当地国家 的时间字符串。
     *
     * @param strDate       日期时间字符串
     * @param svrPattern    server 使用的格式化方式
     * @param targetPattern 目标字符串格式化方式
     * @return String date&time . 如果提供的日期字符串不能被解析，则返回空字符串。
     */
    public static String correctSvrTime(@NonNull String strDate, @Nullable String svrPattern,
                                        @Nullable String targetPattern) {
        if (EmptyUtils.isEmpty(svrPattern)) svrPattern = DEFAULT_PATTERN;
        if (EmptyUtils.isEmpty(targetPattern)) targetPattern = DEFAULT_PATTERN;

        SimpleDateFormat sdf = new SimpleDateFormat(svrPattern, Locale.US);
        TimeZone svrTimeZone = TimeZone.getTimeZone(InfoProvider.getInstance().getTimeZone());
        sdf.setTimeZone(svrTimeZone);

        try {
            Date date = sdf.parse(strDate);
            return (new SimpleDateFormat(targetPattern, Locale.getDefault())).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            Logger.e("The server time cannot be parsed.");
            return "";
        }
    }

    /**
     * Equals to method {@code correctSvrTime(String, String, String}
     */
    public static String correctSvrTime(@NonNull String strDate) {
        return correctSvrTime(strDate, null, null);
    }

}
