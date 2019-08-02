package oysd.com.trade_app.util;

import java.util.Locale;

/**
 * Phone utils.
 * Created by Liam on 2018/8/15
 */
public final class PhoneUtils {

    private PhoneUtils() {
        // Prevents from being instantiated.
    }

    public static String getSystemLang() {
        Locale locale = Locale.getDefault();
        return locale.getLanguage();
    }

}
