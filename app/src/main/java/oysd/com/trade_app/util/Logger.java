package oysd.com.trade_app.util;

import android.util.Log;

import oysd.com.trade_app.Constants;


/**
 * @author houmingkuan
 * @time 2018/7/10
 * @desc 一个简单的日志功能。
 */
public final class Logger {

    private Logger() {
        // Prevent from being instantiated.
    }

    //正式环境不打印日志
    private static final boolean DEBUG = Constants.TEST_MODE;
    private static final String TAG = "Logger";

    // Fix logs "line wrap" problem.
    private static String[] prefix = {". ", " ."};
    private static int index = 0;

    private static String getChangedTag(String tag) {
        index = index ^ 1;
        return prefix[index] + tag;
    }

    // Returns a string which consist of class name and method name and so on.
    private static String getLogInfo() {
        StackTraceElement[] elements = new Throwable().getStackTrace();

        int offset = getStackOffset(elements);
        StackTraceElement element = elements[offset];

        return "[" + element.getClassName() +
                "." + element.getMethodName() +
                "() :: " + element.getLineNumber() +
                "]";
    }

    // Determines the start point stack trace.
    private static int getStackOffset(StackTraceElement[] elements) {
        String name = Logger.class.getName();
        int size = elements.length;

        for (int i = 0; i < size; i++) {
            if (name.equals(elements[i].getClassName())
                    && !name.equals(elements[i + 1].getClassName())) {
                return ++i;
            }
        }
        return 0;
    }

    /**
     * Outputs log of level "i".
     * Only use when you want to display in the release versions.
     */
    public static void xi(String log) {
        Log.i(TAG, log);
    }

    /**
     * Outputs log of level "e".
     * Only use when you want to display in the release versions.
     */
    public static void xe(String log) {
        Log.e(TAG, log);
    }

    // Below methods are for debugging.

    public static void i(String log) {
        i(TAG, log);
    }

    public static void i(String tag, String log) {
        if (DEBUG) {
            Log.println(Log.INFO, getChangedTag(tag), getLogInfo());
            Log.println(Log.INFO, getChangedTag(tag), log);
        }
    }

    public static void d(String log) {
        d(TAG, log);
    }

    public static void d(String tag, String log) {
        if (DEBUG) {
            Log.println(Log.DEBUG, getChangedTag(tag), getLogInfo());
            Log.println(Log.DEBUG, getChangedTag(tag), log);
        }
    }

    public static void v(String log) {
        v(TAG, log);
    }

    public static void v(String tag, String log) {
        if (DEBUG) {
            Log.println(Log.VERBOSE, getChangedTag(tag), getLogInfo());
            Log.println(Log.VERBOSE, getChangedTag(tag), log);
        }
    }

    public static void e(String log) {
        e(TAG, log);
    }

    public static void e(String tag, String log) {
        if (DEBUG) {
            Log.println(Log.ERROR, getChangedTag(tag), getLogInfo());
            Log.println(Log.ERROR, getChangedTag(tag), log);
        }
    }

    /**
     * Same as method {@code dAll(String tag, String log)}.
     */
    public static void dAll(String log) {
        dAll(TAG, log);
    }

    /**
     * Outputs all log in console window.
     */
    public static void dAll(String tag, String log) {
        if (DEBUG) {
            Log.println(Log.DEBUG, getChangedTag(tag), getLogInfo());

            if (log.length() >= 3000) {
                Log.println(Log.DEBUG, getChangedTag(tag), log.substring(0, 3000));
                log = log.substring(3000);
            }
            Log.println(Log.DEBUG, getChangedTag(tag), log);
        }
    }

}
