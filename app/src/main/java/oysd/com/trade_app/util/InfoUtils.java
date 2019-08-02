package oysd.com.trade_app.util;

import oysd.com.trade_app.Values;
import oysd.com.trade_app.common.InfoProvider;

/**
 * 一些数值的判断方法.
 * Created by Liam on 2018/9/28.
 */
public final class InfoUtils {

    private InfoUtils() {
        // Prevents from being instantiated.
    }

    /**
     * 获取当前 app 所使用的语言。
     */
    public static String getLanguage() {
        String language = InfoProvider.getInstance().getLanguage();

        if (EmptyUtils.isEmpty(language)) {
            language = PhoneUtils.getSystemLang();
            // 获取系统语言只区分 zh / en
            language = Values.Language.ZH.equals(language) ?
                    Values.Language.ZH : Values.Language.EN;
        }
        return language;
    }

    /**
     * 获取当取设置的货币类型.
     */
    public static String getLegalTender() {
        String legalTender = InfoProvider.getInstance().getLegalTender();
        if (EmptyUtils.isEmpty(legalTender)) {
            //默认HKD
            legalTender = Values.Language.ZH.equals(getLanguage()) ?
                    Values.Currency.HKD_VALUE : Values.Currency.USD_VALUE;

        }
        return legalTender;
    }


}
