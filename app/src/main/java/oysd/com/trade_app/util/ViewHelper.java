package oysd.com.trade_app.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import oysd.com.trade_app.R;
import oysd.com.trade_app.Values;
import oysd.com.trade_app.base.BaseAdapter;

/**
 * 用于对 view 进行各种检查。
 * Created by Liam on 2018/8/23.
 */
public final class ViewHelper {

    private ViewHelper() {
        // Prevents from being instantiated.
    }


    /**
     * 返回 currency name 对应的 currency symbol.
     *
     * @return A$ / $ / ¥ / HK$
     */
    public static String getCurrencySymbolByName(@NonNull String currencyName) {
        switch (currencyName) {
            case Values.Currency.HKD_VALUE:
                return Values.Currency.HKD_SYMBOL;
            case Values.Currency.AUD_VALUE:
                return Values.Currency.AUD_SYMBOL;

            case Values.Currency.USD_VALUE:
                return Values.Currency.USD_SYMBOL;

            case Values.Currency.CNY_VALUE:
                return Values.Currency.CNY_SYMBOL;

            default:
                return Values.Currency.CNY_SYMBOL;
        }
    }

    /**
     * 返回 currency Id 对应的 currency symbol.
     *
     * @return A$ / $ / ¥/ HK$
     */
    public static String getCurrencySymbolById(int currencyId) {
        switch (currencyId) {
            case Values.Currency.HKD:
                return Values.Currency.HKD_SYMBOL;
            case Values.Currency.AUD:
                return Values.Currency.AUD_SYMBOL;
            case Values.Currency.USD:
                return Values.Currency.USD_SYMBOL;
            case Values.Currency.CNY:
                return Values.Currency.CNY_SYMBOL;
            default:
                return Values.Currency.CNY_SYMBOL;
        }
    }

    /**
     * 返回 currency name 对应的 currency Id.
     *
     * @return 1：cny; 2：usd; 3:aud;4HK$;
     */
    public static int getCurrencyIdByName(@NonNull String currencyName) {
        switch (currencyName) {
            case Values.Currency.AUD_VALUE:
                return Values.Currency.AUD;
            case Values.Currency.HKD_VALUE:
                return Values.Currency.HKD;

            case Values.Currency.USD_VALUE:
                return Values.Currency.USD;

            case Values.Currency.CNY_VALUE:
                return Values.Currency.CNY;

            default:
                return Values.Currency.CNY;
        }
    }

    /**
     * 给 copy icon 设置监听器，实现复制内容到剪贴板功能。
     */
    public static void setCopyListener(@NonNull final Context context, @NonNull final ImageView iconCopy,
                                       @NonNull final TextView textView) {
        iconCopy.setOnClickListener(v -> {
            ClipboardManager cbm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

            if (cbm == null) {
                ToastUtils.showShort(context, "复制失败!");
                return;
            }

            String content = textView.getText().toString();
            cbm.setPrimaryClip(ClipData.newPlainText(null, content));
            ToastUtils.showShort(context, "复制成功!");
        });
    }

    /**
     * 获取了网络数据之后更新 RecyclerView 界面。
     * not completed.
     */
    public static <T> void updateDataSet(@NonNull SmartRefreshLayout refreshLayout,
                                         @NonNull BaseAdapter<T> adapter,
                                         @NonNull List<T> list, int page) {
        if (page == 1) {
            adapter.setDataSet(list);
        }
    }

    /**
     * 检查 EditText 是否为空（用户没有输入任何内容）。
     *
     * @param context  Context
     * @param editText EditText
     * @return true 表示用户未输入；false 表示用户已经输入
     */
    public static boolean isEditEmpty(@NonNull Context context, @NonNull EditText editText) {
        if (EmptyUtils.isEmpty(editText.getText())) {
            // 获取焦点
            editText.requestFocus();
            ToastUtils.showLong(context, context.getString(R.string.err_content_not_empty));
            return true;
        }
        return false;
    }

    /**
     * 检查 多个 EditText 是否为空（用户没有输入任何内容）。
     * 焦点会定位到第一个为空的 EditText 上。
     *
     * @param context   Context
     * @param editTexts EditText array
     * @return true 表示用户未输入；false 表示用户已经输入
     */
    public static boolean isEditEmpty(@NonNull Context context, @NonNull EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (isEditEmpty(context, editText)) return true;
        }
        return false;
    }

    /**
     * 检查输入的 “最大值” 是否大于或等于（>=） “最小值”。
     *
     * @param context Context
     * @param maxEdit "max" EditText
     * @param minEdit "min" EditText
     * @return true 表示 max 大于或等于 min; false otherwise.
     */
    public static boolean isMaxGreaterThanMin(@NonNull Context context, @NonNull EditText maxEdit,
                                              @NonNull EditText minEdit) {
        try {
            double max = Double.valueOf(maxEdit.getText().toString());
            double min = Double.valueOf(minEdit.getText().toString());

            if (max >= min) {
                return true;
            } else {
                maxEdit.requestFocus();
                ToastUtils.showShort(context, "最大交易量要 大于或等于 最小交易量");
                return false;
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * EditText 中输入的数值 是否 大于或等于 指定的值。
     *
     * @return true 大于或等于指定值；false otherwise
     */
    public static boolean isGreaterOrEqual(@NonNull Context ctx, @NonNull EditText edit, double value) {
        boolean result;
        try {
            result = Double.parseDouble(edit.getText().toString()) >= value;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            result = false;
        }

        if (!result) {
            edit.requestFocus();
            ToastUtils.showShort(ctx, "必须大于或等于指定值");
        }
        return result;
    }

    /**
     * EditText 中输入的数值 是否 小于或等于 指定的值。
     *
     * @return true 小于或等于指定值；false otherwise
     */
    public static boolean isLessOrEqual(@NonNull Context ctx, @NonNull EditText edit, double value) {
        boolean result;
        try {
            result = Double.parseDouble(edit.getText().toString()) <= value;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            result = false;
        }

        if (!result) {
            edit.requestFocus();
            ToastUtils.showShort(ctx, "必须小于或等于指定值");
        }
        return result;
    }

    /**
     * 检查 备注 EditText 是否小于 20 字。
     *
     * @param context Context
     * @param remark  备注
     * @return true 表示备注内容小于或等于 20 字；false 大于。
     */
    public static boolean isRemarkLessThan20(@NonNull Context context, @NonNull EditText remark) {
        int len = remark.getText().length();
        if (len <= 20) {
            return true;
        }

        remark.requestFocus();
        ToastUtils.showLong(context, context.getString(R.string.err_remark_less_than_20));
        return false;
    }

    /**
     * 检查两次输入是否一致.
     *
     * @param context Context
     * @param first   第一个 EditText
     * @param second  第二个 EditText
     * @return true 表示两次输入一致；false 不一致。
     */
    public static boolean isTwiceEntrySame(@NonNull Context context, @NonNull EditText first,
                                           @NonNull EditText second) {
        if (first.getText().toString().equals(second.getText().toString())) {
            return true;
        }

        first.requestFocus();
        ToastUtils.showLong(context, context.getString(R.string.err_card_not_same));
        return false;
    }

    /**
     * 检查多个 EditText 中是否有内容存在。
     *
     * @return true 表示有内容；false 表示所有都没有内容。
     */
    public static boolean isContentExists(@NonNull EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (EmptyUtils.isNotEmpty(editText.getText())) return true;
        }
        return false;
    }

}
