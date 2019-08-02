package oysd.com.trade_app.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import oysd.com.trade_app.Constants;
import oysd.com.trade_app.modules.mycenter.view.UrlWebActivity;

/**
 * Intent utils.
 * Created by Liam on 2018/7/24
 */
public final class IntentUtils {

    //用户协议目前还未处理中英文
    public static final String URL_USER_AGREEMENT = Constants.H5_URL + "/html/agreement.html";
    public static final String URL_USER_AGREEMENTE = "";

    private IntentUtils() {
        // Prevents from being instantiated.
    }

    public static void startActivity(@NonNull Context context, @NonNull Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startActivity(@NonNull Context context, @NonNull Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 跳转到指定的 web 页面，使用默认的 UrlWebActivity.
     *
     * @param context Context
     * @param title   页面 title
     * @param url     页面 URL
     */
    public static void jumpTo(@NonNull Context context, @Nullable String title, @NonNull String url) {
        jumpTo(context, UrlWebActivity.class, title, url);
    }

    /**
     * 跳转到指定的 web 页面。
     *
     * @param context           Context
     * @param targetWebActivity 目标 web Activity
     * @param title             页面 title
     * @param url               页面 URL
     */
    public static void jumpTo(@NonNull Context context, @NonNull Class<?> targetWebActivity,
                              @Nullable String title, @NonNull String url) {
        Intent intent = new Intent(context, targetWebActivity);
        intent.putExtra(UrlWebActivity.EXTRA_URL, url);
        intent.putExtra(UrlWebActivity.EXTRA_TITLE, title);
        context.startActivity(intent);
    }

}
