package oysd.com.trade_app.util;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Toast;

/**
 * Toast utils.
 * Created by Liam on 2018/7/20
 */
public final class ToastUtils {

    private ToastUtils() {
        // Prevent from being instantiating.
    }

    public static void showShort(@NonNull Context context, @NonNull CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void showShort(@NonNull Context context, @StringRes int resId) {
        show(context, context.getResources().getString(resId), Toast.LENGTH_SHORT);
    }

    public static void showLong(@NonNull Context context, @NonNull CharSequence text) {
        show(context, text, Toast.LENGTH_LONG);
    }

    public static void showLong(@NonNull Context context, @StringRes int resId) {
        show(context, context.getResources().getString(resId), Toast.LENGTH_LONG);
    }

    private static void show(Context context, CharSequence text, int duration) {
        Toast.makeText(context, text, duration).show();
    }

}
